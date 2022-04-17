package ua.zloydi.recipeapp.data.dto

import ua.zloydi.recipeapp.data.dto.recipes.RecipeItemDTO

data class QueryDTO(
    val from: Int,
    val to: Int,
    val count: Int,
    val _links: LinksDTO,
    val hits: Array<HitDTO<RecipeItemDTO>>?= null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QueryDTO

        return _links != other._links
    }

    override fun hashCode(): Int {
        return _links.hashCode()
    }
}
