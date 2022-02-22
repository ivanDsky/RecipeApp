package ua.zloydi.recipeapp.domain.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ua.zloydi.recipeapp.data.dto.QueryDTO
import ua.zloydi.recipeapp.domain.retrofit.RetrofitConstants.QUERY_PREFIX

interface RecipeApi {
    @GET(QUERY_PREFIX)
    suspend fun queryFilters(
        @Query("q") query: String,
        @Query("field") fields: Array<String>,
        @Query("cuisineType") cuisineType: String? = null,
        @Query("mealType") mealType: String? = null,
        @Query("dishType") dishType: String? = null,
    ): Response<QueryDTO>
}