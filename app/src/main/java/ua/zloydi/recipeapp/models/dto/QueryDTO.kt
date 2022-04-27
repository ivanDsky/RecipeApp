package ua.zloydi.recipeapp.models.dto

import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO

data class QueryDTO(
    val from: Int,
    val to: Int,
    val count: Int,
    val _links: LinksDTO,
    val hits: List<HitDTO<RecipeItemDTO>>,
)