package ua.zloydi.recipeapp.models.dto.recipes

import ua.zloydi.recipeapp.models.dto.IngredientDTO

data class RecipeDetailDTO(
    val uri: String? = null,
    val source: String? = null,
    val url: String? = null,
    val ingredients: Array<IngredientDTO>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeItemDTO

        if (uri != other.uri) return false

        return true
    }

    override fun hashCode(): Int = uri?.hashCode() ?: 0

}