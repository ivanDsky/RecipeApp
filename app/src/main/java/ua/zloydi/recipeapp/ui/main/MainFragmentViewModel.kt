package ua.zloydi.recipeapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.*
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import android.view.MenuItem as AndroidMenuItem


class MainFragmentViewModel : ViewModel(), IChildNavigation, IParentNavigation{
    companion object{
        val defaultScreen = Search
        val screens = listOf(Search, Category, Bookmarks)
    }

    private val _navigationActions = Channel<NavigationItem>()
    val navigationActions = _navigationActions.consumeAsFlow()
    private val _currentScreenFlow = MutableStateFlow<AddItem>(defaultScreen)
    val currentScreenFlow = _currentScreenFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _navigationActions.send(defaultScreen)
        }
    }

    fun onMenuSelected(menuItem: AndroidMenuItem){
        popToMenu()
        val current = currentScreenFlow.value
        if(current is MenuItem && current.id == menuItem.itemId)
            return

        Log.d("Debug141", "onMenuSelected: ${menuItem.itemId}")
        screens.find { it.id == menuItem.itemId } ?.let {
            _navigationActions.trySendBlocking(it)
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }

    private fun sendNavigationAction(actionItem: NavigationActionItem) = actionItem.actions.forEach{
        _navigationActions.trySendBlocking(it)
    }

    override fun openDetail(item: RecipeItemDTO) {
        _navigationActions.trySendBlocking(Detail(currentScreenFlow.value, item))
    }

    override fun openCategory(searchCategory: RecipeQuery.Category) {
        _navigationActions.trySendBlocking(CategoryItem(currentScreenFlow.value, searchCategory))
    }

    override fun openParent(){
        val current = currentScreenFlow.value as? ChildItem ?: return
        sendNavigationAction(PopToParentItem(current))
    }

    private fun popToMenu(){
        while (currentScreenFlow.value is ChildItem)
            openParent()
    }

    fun navigationSelect(item: AddItem) {_currentScreenFlow.value = item}
}