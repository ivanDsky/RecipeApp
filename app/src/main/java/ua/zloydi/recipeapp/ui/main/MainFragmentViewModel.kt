package ua.zloydi.recipeapp.ui.main

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.ui.TestFragment
import java.util.NoSuchElementException

class MainFragmentViewModel : ViewModel(){
    companion object{
        private val mainNavigationItem = NavigationItem(TestFragment(), R.string.main_screen, R.id.mainScreen)
        val screens = arrayOf(
            mainNavigationItem,
            NavigationItem(TestFragment(), R.string.category, R.id.categoryScreen),
            NavigationItem(TestFragment(), R.string.bookmarks, R.id.bookmarksScreen),
        )
    }

    private val _navigationScreenFlow = MutableStateFlow(mainNavigationItem)
    val navigationScreenFlow : StateFlow<NavigationItem> get() = _navigationScreenFlow

    fun onMenuSelected(menuItem: MenuItem){
        Log.d("Debug141", "onMenuSelected: ${menuItem.itemId}")
        if(_navigationScreenFlow.value.id == menuItem.itemId)
            return

        screens.find { it.id == menuItem.itemId } ?.let {
            _navigationScreenFlow.value = it
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }
}