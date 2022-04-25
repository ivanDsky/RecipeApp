package ua.zloydi.recipeapp.data.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.zloydi.recipeapp.data.retrofit.RetrofitConstants.QUERY_PREFIX
import ua.zloydi.recipeapp.data.retrofit.RetrofitConstants.RECIPES
import ua.zloydi.recipeapp.models.dto.HitDTO
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO

interface RecipeApi {
    @GET("$RECIPES?$QUERY_PREFIX")
    suspend fun queryFilters(
        @Query("q") query: String,
        @Query("field") fields: List<String>,
        @Query("_cont") nextHash: String? = null,
        @Query("cuisineType") cuisineType: List<String>,
        @Query("mealType") mealType: List<String>,
        @Query("dishType") dishType: List<String>,
    ): Response<QueryDTO>

    @GET("$RECIPES/{id}/?$QUERY_PREFIX")
    suspend fun queryFilters(
        @Path("id") id: String,
        @Query("field") fields: List<String>,
    ): Response<HitDTO<RecipeDetailDTO>>

}