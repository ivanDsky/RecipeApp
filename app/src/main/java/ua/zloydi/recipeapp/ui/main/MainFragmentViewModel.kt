package ua.zloydi.recipeapp.ui.main

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.data.NavigationItem.*
import ua.zloydi.recipeapp.ui.data.RecipeItemUI


class MainFragmentViewModel : ViewModel(), IChildNavigation{
    companion object{
        val defaultScreen = Search
        val screens = listOf(Search,Category,Bookmarks)
    }

    private val _navigationScreenFlow = MutableStateFlow<NavigationItem>(defaultScreen)
    val navigationScreenFlow : StateFlow<NavigationItem> get() = _navigationScreenFlow

    fun onMenuSelected(menuItem: MenuItem){
        if(_navigationScreenFlow.value.id == menuItem.itemId)
            return

        Log.d("Debug141", "onMenuSelected: ${menuItem.itemId}")
        screens.find { it.id == menuItem.itemId } ?.let {
            _navigationScreenFlow.value = it
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }

    override fun openDetail(item: RecipeItemUI) {
        _navigationScreenFlow.value = Detail(navigationScreenFlow.value, item)
    }
}