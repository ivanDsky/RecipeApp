package ua.zloydi.recipeapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import ua.zloydi.recipeapp.data.AddItem
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.data.RemoveItem

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerId : Int
) {
    fun bindNavigation(item: NavigationItem){
        when(item){
            is AddItem -> addItem(item)
            is RemoveItem -> removeItem(item)
        }
    }

    private fun FragmentTransaction.hideAll() = fragmentManager.fragments.forEach { hide(it) }

    private fun FragmentTransaction.add(addItem: AddItem) =
        add(containerId, addItem.fragmentFactory(), addItem.tag)

    private fun get(addItem: AddItem) =
        fragmentManager.findFragmentByTag(addItem.tag)

    private fun addItem(addItem: AddItem) = fragmentManager.commit {
        hideAll()
        val fragment = get(addItem)
        if (fragment != null) show(fragment) else add(addItem)
    }

    private fun removeItem(removeItem: RemoveItem) = fragmentManager.commit {
        val fragment = get(removeItem.addItem) ?: return@commit
        remove(fragment)
    }
}