package ua.zloydi.recipeapp.ui.data

import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import java.io.Serializable

data class RecipeUI(
    val title: String? = null,
    val image: String? = null,
    val source: String? = null,
    val description: String? = null,
    val url: String? = null,
    val ingredients: Array<IngredientUI>? = null,
    val calories: Float? = null,
    val totalTime: Float? = null,
    val cuisineType: Array<CuisineUI>? = null,
    val mealType: Array<MealUI>? = null,
    val dishType: Array<DishUI>? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeUI

        if (image != other.image) return false
        if (source != other.source) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image?.hashCode() ?: 0
        result = 31 * result + (source?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }
}
