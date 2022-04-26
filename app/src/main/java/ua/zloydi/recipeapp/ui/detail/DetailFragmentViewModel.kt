package ua.zloydi.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filter_types.*
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.ui.main.IParentNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class DetailFragmentViewModel(
    recipe: RecipeItemDTO,
    private val repository: RecipeRepository,
    private val navigation: IParentNavigation,
) : ViewModel(), IParentNavigation by navigation {
    val recipeUI = viewModelScope.async(Dispatchers.IO){
        val detailRecipe = repository.query(
            RecipeQuery.Recipe(recipe.id ?: return@async null)
        ) ?: return@async null
        val categories = recipe.dishType.toUI(Dish.mapper,::DishUI){ Filter(categories = it) }
        val meals = recipe.mealType.toUI(Meal.mapper,::MealUI){ Filter(meals = it) }
        val cuisines = recipe.cuisineType.toUI(Cuisine.mapper,::CuisineUI){ Filter(cuisines = it) }
        detailRecipe.toUI(recipe, categories, meals, cuisines)
    }

    private fun <T : FilterType, V> List<String>.toUI(
        mapper: Mapper<T>,
        uiFactory: (String, () -> Unit) -> V,
        filterFactory: (List<T>) -> Filter,
    ) = flatMap { it.split('/') }
        .map {
            val item = mapper[it]
            uiFactory(item.label){
                navigation.openSearch(SearchFilter(filter = filterFactory(listOf(item))))
            }
        }


    class Factory(
        private val recipe: RecipeItemDTO,
        private val repository: RecipeRepository,
        private val navigation: IParentNavigation,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java))
                return DetailFragmentViewModel(recipe, repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}