package ua.zloydi.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.filter_types.Dish
import ua.zloydi.recipeapp.models.filter_types.Filter
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class TestFragmentVM(
    private val repository: RecipeRepository,
    private val childNavigation: IChildNavigation,
) : ViewModel(){
    private val pager = Pager(PagingConfig(20,20,false,40),null){
        RecipeSource(repository,RecipeQuery.Search("", filter = Filter(categories = listOf(Dish.AlcoholCocktail))))
    }
    val flow = pager.flow.map {pagingData ->
        pagingData.map { it.toUI {
            childNavigation.openDetail(it)
        } }
    }.cachedIn(viewModelScope)

    class Factory(private val repository: RecipeRepository, private val navigation: IChildNavigation) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(TestFragmentVM::class.java))
                return TestFragmentVM(repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}