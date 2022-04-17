package ua.zloydi.recipeapp.data.repository

import android.util.Log
import retrofit2.Response
import ua.zloydi.recipeapp.data.dto.QueryDTO
import ua.zloydi.recipeapp.data.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.data.error.Error
import ua.zloydi.recipeapp.data.error.ErrorService
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.data.retrofit.RetrofitService
import java.io.IOException

class RecipeRepository(
    private val retrofitService: RetrofitService,
    private val errorService: ErrorService
) {
    private suspend fun submitErrorMessage(message: String?) {
        errorService.submitError(Error.MessageError(message ?: "Response unknown error"))
    }

    suspend fun query(query: RecipeQuery.Recipe) : RecipeDetailDTO?{
        return query(retrofitService.query(query))?.recipe
    }
    suspend fun query(query: RecipeQuery.Search, nextHash: String? = null) : QueryDTO?{
        return query(retrofitService.query(query, nextHash))
    }

    private suspend fun <T> query(
        response: Response<T>
    ): T? {
        try {
            if (!response.isSuccessful) {
                Log.e("Debug141", "query: ${response.raw()}")
                submitErrorMessage("Error code = ${response.code()}")
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
}