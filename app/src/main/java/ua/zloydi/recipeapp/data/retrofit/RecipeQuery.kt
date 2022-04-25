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
        val cuisineType: List<Cuisine> = emptyList(),
        val mealType: List<Meal> = emptyList(),
        val dishType: List<Dish> = emptyList(),
    ) : RecipeQuery() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Search

            if (query != other.query) return false

            return true
        }

        override fun hashCode(): Int {
            return query.hashCode()
        }
    }

    data class Recipe(
        val id: String
    ) : RecipeQuery()
}

fun RecipeQuery.Category.toSearch() = RecipeQuery.Search(
    query = "",
    dishType = listOf(dishType)
)
