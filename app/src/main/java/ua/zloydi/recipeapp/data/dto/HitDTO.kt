package ua.zloydi.recipeapp.data.dto

data class HitDTO<T>(
    val recipe: T,
    val _links: LinksDTO,
)
