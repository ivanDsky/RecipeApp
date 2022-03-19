package ua.zloydi.recipeapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ua.zloydi.recipeapp.data.dto.QueryDTO
import ua.zloydi.recipeapp.data.dto.RecipeDTO
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery

class RecipeSource(private val repository: RecipeRepository,private val query: RecipeQuery) : PagingSource<String,RecipeDTO>(){
    override fun getRefreshKey(state: PagingState<String, RecipeDTO>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RecipeDTO> {
        val response: QueryDTO = repository.query(query, params.key) ?: return LoadResult.Error(Exception("No data"))
        Log.d("Debug141", "load: from=${response.from}, to=${response.to}, count=${response.count}")
        val recipes = response.hits?.map { it.recipe } ?: return LoadResult.Error(Exception("No elements"))
        val next = response._links.next?.hash

        return LoadResult.Page(recipes, null, next)
    }
}