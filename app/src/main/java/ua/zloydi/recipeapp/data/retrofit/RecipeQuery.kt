package ua.zloydi.recipeapp.data.retrofit

import ua.zloydi.recipeapp.models.filterTypes.Dish
import ua.zloydi.recipeapp.models.filterTypes.Filter
import ua.zloydi.recipeapp.models.filterTypes.SearchFilter

sealed class RecipeQuery {
    data class Category(
        val dishType: Dish
    ) : RecipeQuery(), java.io.Serializable

    data class Search(
        val searchFilter: SearchFilter
    ) : RecipeQuery()

    data class Recipe(
        val id: String
    ) : RecipeQuery()

    data class RecipeItem(
        val id: String
    ) : RecipeQuery()
}

fun RecipeQuery.Category.toSearch() = RecipeQuery.Search(
    SearchFilter(filter = Filter(categories = listOf(dishType)))
)
