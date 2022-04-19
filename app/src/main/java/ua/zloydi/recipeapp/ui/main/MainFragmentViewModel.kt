package ua.zloydi.recipeapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.zloydi.recipeapp.data.*
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import android.view.MenuItem as AndroidMenuItem


class MainFragmentViewModel : ViewModel(), IChildNavigation, IParentNavigation{
    companion object{
        val defaultScreen = Search
        val screens = listOf(Search,Category, Bookmarks)
    }

    private val _navigationScreenFlow = MutableStateFlow<NavigationItem>(defaultScreen)
    val navigationScreenFlow : StateFlow<NavigationItem> get() = _navigationScreenFlow

    fun onMenuSelected(menuItem: AndroidMenuItem){
        val current = _navigationScreenFlow.value
        if(current is MenuItem && current.id == menuItem.itemId)
            return

        Log.d("Debug141", "onMenuSelected: ${menuItem.itemId}")
        screens.find { it.id == menuItem.itemId } ?.let {
            _navigationScreenFlow.value = it
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }

    override fun openDetail(item: RecipeItemUI) {
        _navigationScreenFlow.value = Detail(navigationScreenFlow.value, item)
    }

    override fun openParent(){
        val current = _navigationScreenFlow.value as? ChildItem ?: return
        _navigationScreenFlow.value = PopToParentItem(current)
    }
}