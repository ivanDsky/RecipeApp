package ua.zloydi.recipeapp.ui.main

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.zloydi.recipeapp.data.NavigationItem
import java.util.NoSuchElementException

class MainFragmentViewModel : ViewModel(){
    companion object{
        val screens = arrayOf<NavigationItem>()
    }

    private val _navigationScreenFlow = MutableStateFlow(screens.first())
    val navigationScreenFlow : StateFlow<NavigationItem> get() = _navigationScreenFlow

    fun onMenuSelected(menuItem: MenuItem){
        if(_navigationScreenFlow.value.id == menuItem.itemId)
            return

        screens.find { it.id == menuItem.itemId } ?.let {
            _navigationScreenFlow.value = it
        } ?: throw NoSuchElementException("No element with id: ${menuItem.itemId}")
    }
}