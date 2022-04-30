package ua.zloydi.recipeapp.utils

import androidx.annotation.IdRes
import androidx.fragment.app.*
import ua.zloydi.recipeapp.data.AddItem
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.data.RemoveItem
import ua.zloydi.recipeapp.data.SendItem

class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerId : Int
) {
    fun bindNavigation(item: NavigationItem) = when(item){
        is AddItem<*> -> addItem(item)
        is RemoveItem -> removeItem(item)
        is SendItem<*> -> sendItem(item)
    }

    private fun FragmentTransaction.hideAll() = fragmentManager.fragments.forEach { hide(it) }

    private fun FragmentTransaction.add(addItem: AddItem<*>) =
        add(containerId, addItem.fragmentFactory(), addItem.tag)

    private fun<F : Fragment> get(addItem: AddItem<F>): F? =
        fragmentManager.findFragmentByTag(addItem.tag) as F?

    private fun addItem(addItem: AddItem<*>) = fragmentManager.commitNow(allowStateLoss = true) {
        hideAll()
        val fragment = get(addItem)
        if (addItem.newInstance && fragment != null) remove(fragment)
        if (!addItem.newInstance && fragment != null) show(fragment) else add(addItem)
    }

    private fun<F : Fragment> sendItem(sendItem: SendItem<F>){
        sendItem.send(get(sendItem.addItem)?:return)
    }

    private fun removeItem(removeItem: RemoveItem) = fragmentManager.commit {
        val fragment = get(removeItem.addItem) ?: return@commit
        remove(fragment)
    }
}