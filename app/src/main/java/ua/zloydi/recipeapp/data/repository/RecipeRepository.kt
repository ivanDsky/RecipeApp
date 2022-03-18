package ua.zloydi.recipeapp.data.repository

import ua.zloydi.recipeapp.data.dto.QueryDTO
import ua.zloydi.recipeapp.data.error.Error
import ua.zloydi.recipeapp.data.error.ErrorService
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.data.retrofit.RetrofitService
import java.io.IOException

class RecipeRepository(
    private val retrofitService: RetrofitService,
    private val errorService: ErrorService
) {
    suspend fun query(query: RecipeQuery): QueryDTO? {
        try {
            val response = retrofitService.query(query)
            if (!response.isSuccessful) {
                submitErrorMessage(response.errorBody()?.string())
                return null
            }
            if (response.body() == null) {
                submitErrorMessage("Empty response")
                return null
            }
            return response.body()
        } catch (e: IOException) {
            submitErrorMessage(e.message)
            return null
        }
    }

    private suspend fun submitErrorMessage(message: String?) {
        errorService.submitError(Error.MessageError(message ?: "Response unknown error"))
    }
}