package ua.zloydi.recipeapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.ErrorProvider
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.error.Error
import ua.zloydi.recipeapp.models.filter_types.Filter
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class SearchFragmentViewModel(
    private val repository: RecipeRepository,
    private val navigation: IChildNavigation,
) : ViewModel(), IChildNavigation by navigation {
    private val _stateFlow: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())
    val stateFlow = _stateFlow.asStateFlow()

    private val _searchFlow: MutableStateFlow<UpdateFlow> = MutableStateFlow(UpdateFlow())
    val searchFlow = _searchFlow.asStateFlow()

    private var pager: Pager<String, RecipeItemDTO>? = null
    private val pagerConfig = PagingConfig(20, 30, false, 60)

    fun query(queryText: String?) = viewModelScope.launch(Dispatchers.IO) {
        if (queryText.isNullOrBlank())
            ErrorProvider.service.submitError(Error.MessageError("Query is empty"))
        else
            sendQuery(queryText, _stateFlow.value.filter)
    }

    fun filter(filter: Filter) {
        sendQuery(_stateFlow.value.queryText, filter)
    }

    private fun sendQuery(queryText: String, filter: Filter) {
        _stateFlow.update { it.copy(queryText = queryText, filter = filter) }

        Log.d("Debug141", "sendQuery: $queryText")
        val apiQuery = RecipeQuery.Search(queryText,filter)
        pager = Pager(pagerConfig, pagingSourceFactory = { RecipeSource(repository, apiQuery) })

        _searchFlow.update {
            UpdateFlow(getUIFlow())
        }
    }

    private fun getUIFlow() = pager!!.flow.map { pagingData ->
        pagingData.map {
            it.toUI {
                navigation.openDetail(it)
            }
        }
    }.cachedIn(viewModelScope)

    init {
        val state = _stateFlow.value
        sendQuery(state.queryText, state.filter)
    }

    class Factory(
        private val repository: RecipeRepository,
        private val navigation: IChildNavigation,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchFragmentViewModel::class.java))
                return SearchFragmentViewModel(repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}

data class UpdateFlow(
    val flow: Flow<PagingData<RecipeItemUI>>? = null
)

data class SearchState(
    val queryText: String = "App",
    val filter: Filter = Filter(),
)