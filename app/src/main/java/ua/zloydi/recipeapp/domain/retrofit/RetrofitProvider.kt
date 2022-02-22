package ua.zloydi.recipeapp.domain.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.zloydi.recipeapp.domain.RetrofitService
import ua.zloydi.recipeapp.domain.retrofit.RetrofitConstants.BASE_URl

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object RetrofitProvider {
    private val api: RecipeApi = retrofit.create(RecipeApi::class.java)
    val service = RetrofitService(api)
}