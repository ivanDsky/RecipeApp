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
import ua.zloydi.recipeapp.models.filter_types.SearchFilter
import android.view.MenuItem as AndroidMenuItem


class MainFragmentViewModel : ViewModel(), IChildNavigation, IParentNavigation{
    companion object{
        val screens = listOf<AddItem<*>>(Search(), Category, Bookmarks)
        val defaultScreen = screens[1]
    }

    private val _navigationActions = Channel<NavigationItem>()
    val navigationActions = _navigationActions.consumeAsFlow()
    private val _currentScreenFlow = MutableStateFlow(defaultScreen)
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
        screens.find { (it as MenuItem).id == menuItem.itemId } ?.let {
            _navigationActions.trySendBlocking(it)
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }

    private fun sendNavigationAction(actionItem: NavigationActionItem){
        actionItem.actions?.forEach {
            if (it is NavigationItem)
                _navigationActions.trySendBlocking(it)
            else
                sendNavigationAction(it)
        }
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

    override fun openSearch(searchFilter: SearchFilter) {
        sendNavigationAction(GoToSearch(currentScreenFlow.value, Search(searchFilter)))
    }

    private fun popToMenu(){
        sendNavigationAction(PopBackStack(currentScreenFlow.value))
    }

    fun navigationSelect(item: AddItem<*>) {_currentScreenFlow.value = item}
}