package ua.zloydi.recipeapp.data.retrofit

import ua.zloydi.recipeapp.data.filter_types.Cuisine
import ua.zloydi.recipeapp.data.filter_types.Dish
import ua.zloydi.recipeapp.data.filter_types.Meal

sealed class RecipeQuery {
    data class Search(
        val query: String,
        val cuisineType: Cuisine? = null,
        val mealType: Meal? = null,
        val dishType: Dish? = null,
    ) : RecipeQuery()

    data class Recipe(
        val id: String
    ) : RecipeQuery()
}
