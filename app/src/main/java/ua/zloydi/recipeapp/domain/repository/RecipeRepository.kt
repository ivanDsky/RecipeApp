package ua.zloydi.recipeapp.domain.repository

import ua.zloydi.recipeapp.data.dto.QueryDTO
import ua.zloydi.recipeapp.data.errors.Error
import ua.zloydi.recipeapp.domain.error.ErrorService
import ua.zloydi.recipeapp.domain.retrofit.RecipeQuery
import ua.zloydi.recipeapp.domain.retrofit.RetrofitService

class RecipeRepository(
    private val retrofitService: RetrofitService,
    private val errorService: ErrorService
) {
    suspend fun query(query: RecipeQuery) : QueryDTO?{
        val response = retrofitService.query(query)
        if(!response.isSuccessful){
            errorService.submitError(Error.MessageError(response.errorBody()?.string() ?: "Response unknown error"))
            return null
        }
        if(response.body() == null){
            errorService.submitError(Error.MessageError("Empty response"))
            return null
        }
        return response.body()
    }
}