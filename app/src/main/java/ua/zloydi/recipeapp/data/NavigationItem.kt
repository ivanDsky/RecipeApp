package ua.zloydi.recipeapp.data

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.ui.TestFragment
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.detail.DetailFragment
import ua.zloydi.recipeapp.ui.search.SearchFragment

sealed interface NavigationItem

sealed class AddItem : NavigationItem{
    abstract val tag: String
    abstract val fragmentFactory: () -> Fragment
}

sealed class MenuItem : AddItem(){
    @get:IdRes
    abstract val id: Int
    @get:StringRes
    abstract val title: Int
}

class RemoveItem(val addItem : AddItem) : NavigationItem

abstract class ActionItem : NavigationItem{
    abstract val actions : List<NavigationItem>
}

object Search : MenuItem() {
    override val title = R.string.main_screen
    override val id = R.id.mainScreen
    override val tag = "Search"
    override val fragmentFactory = { SearchFragment() }
}

object Category : MenuItem(){
    override val title = R.string.category
    override val id = R.id.categoryScreen
    override val tag = "Category"
    override val fragmentFactory = { TestFragment() }
}

object Bookmarks : MenuItem() {
    override val title = R.string.bookmarks
    override val id = R.id.bookmarksScreen
    override val tag = "Bookmarks"
    override val fragmentFactory = { TestFragment() }
}

abstract class ChildItem(val parent: NavigationItem) : AddItem()

class PopToParentItem(childItem: ChildItem) : ActionItem(){
    override val actions = listOf(RemoveItem(childItem), childItem.parent)
}

class Detail(parent: NavigationItem, private val item: RecipeItemUI) : ChildItem(parent) {
    override val tag = "Detail"
    override val fragmentFactory = { DetailFragment.create(item)}
}