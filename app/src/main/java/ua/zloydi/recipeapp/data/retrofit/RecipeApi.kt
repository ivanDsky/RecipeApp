package ua.zloydi.recipeapp.data.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.zloydi.recipeapp.data.dto.HitDTO
import ua.zloydi.recipeapp.data.dto.QueryDTO
import ua.zloydi.recipeapp.data.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.data.retrofit.RetrofitConstants.QUERY_PREFIX
import ua.zloydi.recipeapp.data.retrofit.RetrofitConstants.RECIPES

interface RecipeApi {
    @GET("$RECIPES?$QUERY_PREFIX")
    suspend fun queryFilters(
        @Query("q") query: String,
        @Query("field") fields: Array<String>,
        @Query("_cont") nextHash: String? = null,
        @Query("cuisineType") cuisineType: String? = null,
        @Query("mealType") mealType: String? = null,
        @Query("dishType") dishType: String? = null,
    ): Response<QueryDTO>

    @GET("$RECIPES/{id}/?$QUERY_PREFIX")
    suspend fun queryFilters(
        @Path("id") id: String,
        @Query("field") fields: Array<String>,
    ): Response<HitDTO<RecipeDetailDTO>>

}