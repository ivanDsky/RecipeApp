package ua.zloydi.recipeapp.data

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filterTypes.SearchFilter
import ua.zloydi.recipeapp.ui.bookmarks.BookmarkFragment
import ua.zloydi.recipeapp.ui.categories.list.CategoriesFragment
import ua.zloydi.recipeapp.ui.categories.search.CategorySearchFragment
import ua.zloydi.recipeapp.ui.core.toolbar.IRightButton
import ua.zloydi.recipeapp.ui.detail.DetailFragment
import ua.zloydi.recipeapp.ui.search.SearchFragment
import ua.zloydi.recipeapp.utils.StringItem

sealed interface NavigationItem : NavigationActionItem

sealed class AddItem<out F : Fragment> : NavigationItem {
    open val newInstance = false
    abstract val tag: String
    abstract val fragmentFactory: () -> F
}

sealed interface TitleItem {
    val title: StringItem
}

sealed interface MenuItem {
    @get:IdRes
    val id: Int
}

sealed interface RightToolbarItem<out F : IRightButton>

abstract class SendItem<F : Fragment>(val addItem: AddItem<F>, val send: F.() -> Unit) : NavigationItem

class RemoveItem(val addItem: AddItem<*>) : NavigationItem

interface NavigationActionItem {
    val actions: List<NavigationActionItem>?
        get() = null
}

class Search(val searchFilter: SearchFilter? = null) : AddItem<SearchFragment>(), TitleItem, MenuItem {
    override val title = StringItem.Res(R.string.main_screen)
    override val id = R.id.mainScreen
    override val tag = "Search"
    override val fragmentFactory = { SearchFragment.create(searchFilter) }
}

object Category : AddItem<CategoriesFragment>(), TitleItem, MenuItem {
    override val title = StringItem.Res(R.string.category)
    override val id = R.id.categoryScreen
    override val tag = "Category"
    override val fragmentFactory = { CategoriesFragment() }
}

object Bookmarks : AddItem<BookmarkFragment>(), TitleItem, MenuItem {
    override val title = StringItem.Res(R.string.bookmarks)
    override val id = R.id.bookmarksScreen
    override val tag = "Bookmarks"
    override val fragmentFactory = { BookmarkFragment() }
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
    ChildItem<DetailFragment>(parent), RightToolbarItem<DetailFragment> {
    override val tag = "Detail"
    override val fragmentFactory = { DetailFragment.create(item) }
}

class DetailId(parent: AddItem<*>, private val id: String) :
    ChildItem<DetailFragment>(parent), RightToolbarItem<DetailFragment>{
    override val newInstance = true
    override val tag = "Detail"
    override val fragmentFactory = { DetailFragment.create(id) }
}

class CategoryItem(parent: AddItem<*>, private val item: RecipeQuery.Category) :
    ChildItem<CategorySearchFragment>(parent), TitleItem {
    override val title = StringItem.String(item.dishType.label)
    override val tag = "CategoryItem"
    override val fragmentFactory = { CategorySearchFragment.create(item) }
}