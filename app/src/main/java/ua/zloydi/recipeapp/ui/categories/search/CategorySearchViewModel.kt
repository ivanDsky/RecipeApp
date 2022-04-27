package ua.zloydi.recipeapp.ui.categories.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.map
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.data.retrofit.toSearch
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.main.IParentNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class CategorySearchViewModel(
    repository: RecipeRepository,
    searchCategory: RecipeQuery.Category,
    parenNavigation: IParentNavigation,
    childNavigation: IChildNavigation,
) : ViewModel(), IParentNavigation by parenNavigation{

    private val pagerConfig = PagingConfig(20, 30, true, 60)
    private var pager: Pager<String, RecipeItemDTO> =
        Pager(pagerConfig, pagingSourceFactory = {RecipeSource(repository, searchCategory.toSearch())})

    val uiFlow = pager.flow.map { pagingData ->
        pagingData.map { it.toUI {
            childNavigation.openDetail(it)
        } }
    }

    class Factory(
        private val repository: RecipeRepository,
        private val searchCategory: RecipeQuery.Category,
        private val parenNavigation: IParentNavigation,
        private val childNavigation: IChildNavigation,
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CategorySearchViewModel::class.java))
                return CategorySearchViewModel(repository, searchCategory, parenNavigation, childNavigation) as T
            else
                throw TypeCastException()
        }
    }

}