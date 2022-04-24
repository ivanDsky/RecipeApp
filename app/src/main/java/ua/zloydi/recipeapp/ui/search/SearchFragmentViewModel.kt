package ua.zloydi.recipeapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class SearchFragmentViewModel(
    private val repository: RecipeRepository,
    private val navigation: IChildNavigation
) : ViewModel(), IChildNavigation by navigation {
    private val _stateFlow: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Empty)
    val stateFlow = _stateFlow.asStateFlow()

    private var pager: Pager<String, RecipeItemDTO>? = null
    private val pagerConfig = PagingConfig(20, 30, false, 60)

    fun query(queryText: String?){
        if(queryText.isNullOrBlank())
            _stateFlow.value = SearchState.IncorrectInput("Query is empty")
        else
            sendQuery(queryText)
    }

    private fun sendQuery(queryText: String){
        Log.d("Debug141", "sendQuery: $queryText")
        val apiQuery = RecipeQuery.Search(queryText)
        pager = Pager(pagerConfig, pagingSourceFactory = {RecipeSource(repository,apiQuery)})
        _stateFlow.value = SearchState.Response(getUIFlow().cachedIn(viewModelScope))
    }

    private fun getUIFlow() = pager!!.flow.map { pagingData ->
        pagingData.map { it.toUI{
            navigation.openDetail(it)
        }}
    }

    init {
        sendQuery("App")
    }
    class Factory(private val repository: RecipeRepository, private val navigation: IChildNavigation) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchFragmentViewModel::class.java))
                return SearchFragmentViewModel(repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}



sealed interface SearchState{
    class Response(val flow: Flow<PagingData<RecipeItemUI>>) : SearchState
    class IncorrectInput(val message: String) : SearchState
    object Empty: SearchState
}