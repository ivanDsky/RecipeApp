package ua.zloydi.recipeapp.domain.retrofit

import ua.zloydi.recipeapp.data.filter_types.CuisineType
import ua.zloydi.recipeapp.data.filter_types.DishType
import ua.zloydi.recipeapp.data.filter_types.MealType

data class RecipeQuery(
    val query: String,
    val cuisineType: CuisineType? = null,
    val mealType: MealType? = null,
    val dishType: DishType? = null,
)
