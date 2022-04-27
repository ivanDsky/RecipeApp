package ua.zloydi.recipeapp.data.repository

import android.util.Log
import retrofit2.Response
import ua.zloydi.recipeapp.data.ErrorProvider
import ua.zloydi.recipeapp.data.ErrorService
import ua.zloydi.recipeapp.data.local.room.CacheRecipeDatabase
import ua.zloydi.recipeapp.data.local.room.CacheRecipeProvider
import ua.zloydi.recipeapp.data.local.room.insert
import ua.zloydi.recipeapp.data.local.room.query
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.data.retrofit.RetrofitProvider
import ua.zloydi.recipeapp.data.retrofit.RetrofitService
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.error.Error
import java.io.IOException
import java.net.SocketTimeoutException

class RecipeRepository(
    private val retrofitService: RetrofitService,
    private val database: CacheRecipeDatabase,
    private val errorService: ErrorService
) {
    private suspend fun submitErrorMessage(message: String?) {
        errorService.submitError(Error.MessageError(message ?: "Response unknown error"))
    }

    suspend fun query(query: RecipeQuery.Recipe) : RecipeDetailDTO?{
        val offline = database.recipe().query(query.id)
        if (offline != null) return offline
        val online = query{retrofitService.query(query)}?.recipe
        if (online != null)database.recipe().insert(query.id, online)
        return online
    }

    suspend fun query(query: RecipeQuery.Search, nextHash: String? = null) : QueryDTO?{
        val offline = database.query().query(query.searchFilter,nextHash)
        if (offline != null) return offline
        val online = query{retrofitService.query(query, nextHash)}
        if (online != null)database.query().insert(query.searchFilter, nextHash, online)
        return online
    }

    private suspend fun <T> query(
        query: suspend () -> Response<T>
    ): T? {
        try {
            val response = query()
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
        } catch (e: SocketTimeoutException) {
            submitErrorMessage("Network timeout")
            return null
        } catch (e: IOException) {
            submitErrorMessage(e.message)
            return null
        }
    }
}

object RecipeProvider {
    val repository = RecipeRepository(RetrofitProvider.service,
        CacheRecipeProvider.database,
        ErrorProvider.service)
}
