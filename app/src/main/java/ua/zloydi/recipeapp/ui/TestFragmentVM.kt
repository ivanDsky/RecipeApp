package ua.zloydi.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import ua.zloydi.recipeapp.data.dto.RecipeDTO
import ua.zloydi.recipeapp.data.error.ErrorProvider
import ua.zloydi.recipeapp.data.filter_types.*
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.data.retrofit.RetrofitProvider
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI

class TestFragmentVM : ViewModel(){
    private val repository = RecipeRepository(RetrofitProvider.service, ErrorProvider.service)

    val pager = Pager(PagingConfig(20,20,false,40),null){
        RecipeSource(repository,RecipeQuery("", cuisineType = Cuisine.CentralEurope))
    }
    val flow = pager.flow.cachedIn(viewModelScope)

    val recipes: Deferred<List<RecipeDTO>?> = viewModelScope.async(Dispatchers.IO) {
        repository.query(RecipeQuery("", cuisineType = Cuisine.CentralEurope))?.let { query ->
            query.hits?.map { it.recipe }
        }
    }
    val uiRecipes = viewModelScope.async(Dispatchers.IO){
        val recipe = recipes.await() ?: return@async emptyList()
        recipe.map { recipeDTO ->
            val types = mutableListOf<FilterType?>()
            recipeDTO.mealType?.forEach { it.split("/").forEach { types.add(MealMapper.enum(it)) } }
            recipeDTO.dishType?.forEach { it.split("/").forEach { types.add(DishMapper.enum(it)) } }
            recipeDTO.cuisineType?.forEach { it.split("/").forEach { types.add(CuisineMapper.enum(it)) } }
            RecipeItemUI(recipeDTO.label, recipeDTO.image, recipeDTO.totalTime, types.mapNotNull {
                when(it){
                    is Meal -> MealUI(it.label)
                    is Dish -> DishUI(it.label)
                    is Cuisine -> CuisineUI(it.label)
                    else -> null
                }
            })
        }
    }
}