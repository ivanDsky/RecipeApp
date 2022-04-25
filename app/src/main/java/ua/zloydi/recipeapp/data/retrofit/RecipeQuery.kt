package ua.zloydi.recipeapp.data.retrofit

import ua.zloydi.recipeapp.models.filter_types.Dish
import ua.zloydi.recipeapp.models.filter_types.Filter

sealed class RecipeQuery {
    data class Category(
        val dishType: Dish
    ) : RecipeQuery(), java.io.Serializable

    data class Search(
        val query: String,
        val filter: Filter,
    ) : RecipeQuery()

    data class Recipe(
        val id: String
    ) : RecipeQuery()
}

fun RecipeQuery.Category.toSearch() = RecipeQuery.Search(
    query = "",
    filter = Filter(categories = listOf(dishType))
)
