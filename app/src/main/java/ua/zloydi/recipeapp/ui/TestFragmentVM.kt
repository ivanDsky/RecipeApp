package ua.zloydi.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filter_types.*
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI

class TestFragmentVM : ViewModel(){
    private val repository = RecipeProvider.repository

    val pager = Pager(PagingConfig(20,20,false,40),null){
        RecipeSource(repository,RecipeQuery.Search("", cuisineType = Cuisine.CentralEurope))
    }
    val flow = pager.flow.cachedIn(viewModelScope)

    val recipes: Deferred<List<RecipeItemDTO>?> = viewModelScope.async(Dispatchers.IO) {
        repository.query(RecipeQuery.Search("", cuisineType = Cuisine.CentralEurope))?.let { query ->
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
            RecipeItemUI(recipeDTO.id, recipeDTO.label, recipeDTO.image, recipeDTO.totalTime, types.mapNotNull {
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