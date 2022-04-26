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
    val ingredients: List<IngredientUI>,
    val calories: Float? = null,
    val totalTime: Float? = null,
    val cuisineType: List<CuisineUI>,
    val mealType: List<MealUI>,
    val dishType: List<DishUI>
) : Serializable
