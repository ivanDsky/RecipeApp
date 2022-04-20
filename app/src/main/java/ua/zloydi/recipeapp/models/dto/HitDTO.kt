package ua.zloydi.recipeapp.models.dto

data class HitDTO<T>(
    val recipe: T,
    val _links: LinksDTO,
)
