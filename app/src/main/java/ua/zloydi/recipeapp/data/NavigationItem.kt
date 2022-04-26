package ua.zloydi.recipeapp.data

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filter_types.SearchFilter
import ua.zloydi.recipeapp.ui.TestFragment
import ua.zloydi.recipeapp.ui.categories.list.CategoriesFragment
import ua.zloydi.recipeapp.ui.categories.search.CategorySearchFragment
import ua.zloydi.recipeapp.ui.detail.DetailFragment
import ua.zloydi.recipeapp.ui.search.SearchFragment

sealed interface NavigationItem : NavigationActionItem

sealed class AddItem<out F : Fragment> : NavigationItem {
    abstract val tag: String
    abstract val fragmentFactory: () -> F
}

sealed class MenuItem<out F : Fragment> : AddItem<F>() {
    @get:IdRes
    abstract val id: Int

    @get:StringRes
    abstract val title: Int
}

abstract class SendItem<F : Fragment>(val addItem: AddItem<F>, val send: F.() -> Unit) : NavigationItem

class RemoveItem(val addItem: AddItem<*>) : NavigationItem

interface NavigationActionItem {
    val actions: List<NavigationActionItem>?
        get() = null
}

class Search(val searchFilter: SearchFilter? = null) : MenuItem<SearchFragment>() {
    override val title = R.string.main_screen
    override val id = R.id.mainScreen
    override val tag = "Search"
    override val fragmentFactory = { SearchFragment.create(searchFilter) }
}

object Category : MenuItem<CategoriesFragment>() {
    override val title = R.string.category
    override val id = R.id.categoryScreen
    override val tag = "Category"
    override val fragmentFactory = { CategoriesFragment() }
}

object Bookmarks : MenuItem<TestFragment>() {
    override val title = R.string.bookmarks
    override val id = R.id.bookmarksScreen
    override val tag = "Bookmarks"
    override val fragmentFactory = { TestFragment() }
}

abstract class ChildItem<out F : Fragment>(val parent: AddItem<*>) : AddItem<F>()

class PopToParentItem(childItem: ChildItem<*>) : NavigationActionItem {
    override val actions = listOf(RemoveItem(childItem), childItem.parent)
}

class PopBackStack(item: AddItem<*>) : NavigationActionItem {
    private val _actions = mutableListOf<NavigationItem>()
    init {
        var current: AddItem<*> = item
        while (current is ChildItem<*>){
            _actions.add(RemoveItem(current))
            current = current.parent
        }
        _actions.add(current)
    }
    override val actions = _actions
}

class SearchSend(search: Search) : SendItem<SearchFragment>(search, { setQuery(search.searchFilter) })

class GoToSearch(current: AddItem<*>, search: Search) : NavigationActionItem{
    override val actions = listOf(PopBackStack(current), search, SearchSend(search))
}
class Detail(parent: AddItem<*>, private val item: RecipeItemDTO) :
    ChildItem<DetailFragment>(parent) {
    override val tag = "Detail"
    override val fragmentFactory = { DetailFragment.create(item) }
}

class CategoryItem(parent: AddItem<*>, private val item: RecipeQuery.Category) :
    ChildItem<CategorySearchFragment>(parent) {
    override val tag = "CategoryItem"
    override val fragmentFactory = { CategorySearchFragment.create(item) }
}