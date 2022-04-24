package ua.zloydi.recipeapp.data.retrofit

import ua.zloydi.recipeapp.models.filter_types.Cuisine
import ua.zloydi.recipeapp.models.filter_types.Dish
import ua.zloydi.recipeapp.models.filter_types.Meal

sealed class RecipeQuery {
    data class Category(
        val dishType: Dish
    ) : RecipeQuery(), java.io.Serializable

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

fun RecipeQuery.Category.toSearch() = RecipeQuery.Search(
    query = "",
    dishType = dishType
)
