package ua.zloydi.recipeapp.ui.main

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.data.NavigationItem.*


class MainFragmentViewModel : ViewModel(){
    companion object{
        val defaultScreen = Search
        val screens = listOf(Search,Category,Bookmarks)
    }

    private val _navigationScreenChannel = MutableStateFlow<NavigationItem>(defaultScreen)
    val navigationScreenFlow : StateFlow<NavigationItem> get() = _navigationScreenChannel

    fun onMenuSelected(menuItem: MenuItem){
        if(_navigationScreenChannel.value.id == menuItem.itemId)
            return

        Log.d("Debug141", "onMenuSelected: ${menuItem.itemId}")
        screens.find { it.id == menuItem.itemId } ?.let {
            _navigationScreenChannel.value = it
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }
}