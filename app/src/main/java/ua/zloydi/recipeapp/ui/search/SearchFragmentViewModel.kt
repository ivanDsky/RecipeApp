package ua.zloydi.recipeapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.ErrorProvider
import ua.zloydi.recipeapp.data.local.SettingsRepository
import ua.zloydi.recipeapp.data.paging.RecipeSource
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.error.Error
import ua.zloydi.recipeapp.models.filter_types.Filter
import ua.zloydi.recipeapp.models.filter_types.Meal
import ua.zloydi.recipeapp.models.filter_types.SearchFilter
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class SearchFragmentViewModel(
    searchFilter: SearchFilter?,
    private val settings: SettingsRepository,
    private val repository: RecipeRepository,
    private val navigation: IChildNavigation,
) : ViewModel(), IChildNavigation by navigation {

    private fun getDefaultFilter() =
        settings.getSearchFilter() ?: SearchFilter(filter = Filter(meals = listOf(Meal.Dinner)))
    private val _stateFlow: MutableStateFlow<SearchFilter> =
        MutableStateFlow(searchFilter ?: getDefaultFilter())
    val stateFlow = _stateFlow.asStateFlow()

    private val _searchFlow: MutableStateFlow<Flow<PagingData<RecipeItemUI>>?> = MutableStateFlow(null)
    val searchFlow = _searchFlow.asStateFlow()

    private var pager: Pager<String, RecipeItemDTO>? = null
    private val pagerConfig = PagingConfig(20, 30, false, 60)

    fun query(queryText: String) = viewModelScope.launch(Dispatchers.IO) {
        sendQuery(SearchFilter(queryText, _stateFlow.value.filter))
    }

    fun filter(filter: Filter, queryText: String = _stateFlow.value.search) {
        sendQuery(SearchFilter(queryText, filter))
    }

    fun sendQuery(searchFilter: SearchFilter) = viewModelScope.launch(Dispatchers.IO){
        if(searchFilter.isEmpty()) {
            ErrorProvider.service.submitError(Error.MessageError("No filters and query"))
            return@launch
        }

        _stateFlow.value = searchFilter
        launch { settings.putSearchFilter(searchFilter) }

        val apiQuery = RecipeQuery.Search(searchFilter)
        pager = Pager(pagerConfig, pagingSourceFactory = { RecipeSource(repository, apiQuery) })

        _searchFlow.value = getUIFlow()
    }

    private fun getUIFlow() = pager!!.flow.map { pagingData ->
        pagingData.map {
            it.toUI {
                navigation.openDetail(it)
            }
        }
    }.cachedIn(viewModelScope)

    init {
        sendQuery(_stateFlow.value)
    }

    class Factory(
        private val searchFilter: SearchFilter?,
        private val settings: SettingsRepository,
        private val repository: RecipeRepository,
        private val navigation: IChildNavigation,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchFragmentViewModel::class.java))
                return SearchFragmentViewModel(searchFilter,settings, repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}