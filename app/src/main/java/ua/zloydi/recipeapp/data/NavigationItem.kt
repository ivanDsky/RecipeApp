package ua.zloydi.recipeapp.data

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.ui.TestFragment
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.detail.DetailFragment
import ua.zloydi.recipeapp.ui.search.SearchFragment

sealed interface NavigationItem{
    @get:StringRes val title: Int
    @get:IdRes val id: Int
    val tag: String
    val fragmentFactory: () -> Fragment

    object Search : NavigationItem {
        override val title = R.string.main_screen
        override val id = R.id.mainScreen
        override val tag = "Search"
        override val fragmentFactory = {SearchFragment()}
    }

    object Category : NavigationItem {
        override val title = R.string.category
        override val id = R.id.categoryScreen
        override val tag = "Category"
        override val fragmentFactory = {TestFragment()}
    }

    object Bookmarks : NavigationItem {
        override val title = R.string.bookmarks
        override val id = R.id.bookmarksScreen
        override val tag = "Bookmarks"
        override val fragmentFactory = {TestFragment()}
    }

    class Detail(private val parent: NavigationItem, private val item: RecipeItemUI) : NavigationItem {
        override val title = R.string.bookmarks
        override val id = item.hashCode()
        override val tag = "Detail"
        override val fragmentFactory = {DetailFragment.create(item)}
    }
}