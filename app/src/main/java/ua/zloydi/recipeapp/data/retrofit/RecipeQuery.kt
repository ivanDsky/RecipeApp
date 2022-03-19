package ua.zloydi.recipeapp.data.retrofit

import ua.zloydi.recipeapp.data.filter_types.*

data class RecipeQuery(
    val query: String,
    val cuisineType: Cuisine? = null,
    val mealType: Meal? = null,
    val dishType: Dish? = null,
)
