package ua.zloydi.recipeapp.data.ui

import ua.zloydi.recipeapp.data.filter_types.CuisineType
import ua.zloydi.recipeapp.data.filter_types.DishType
import ua.zloydi.recipeapp.data.filter_types.MealType

data class RecipeUI(
    val title: String? = null,
    val image: String? = null,
    val ingredients: List<IngredientUI>,
    val cuisineType: CuisineType? = null,
    val dishType: DishType? = null,
    val mealType: MealType? = null
)
