package ua.zloydi.recipeapp.domain.repository

import ua.zloydi.recipeapp.domain.retrofit.RecipeQuery
import ua.zloydi.recipeapp.domain.retrofit.RetrofitService

class RecipeRepository(
    private val retrofitService: RetrofitService,
) {
    suspend fun query(query: RecipeQuery) = retrofitService.query(query)
}