package ua.zloydi.recipeapp.models.dto.recipes

import ua.zloydi.recipeapp.models.dto.IngredientDTO

data class RecipeDetailDTO(
    val uri: String? = null,
    val source: String? = null,
    val url: String? = null,
    val dishType: List<String>,
    val mealType: List<String>,
    val cuisineType: List<String>,
    val ingredients: List<IngredientDTO>
)