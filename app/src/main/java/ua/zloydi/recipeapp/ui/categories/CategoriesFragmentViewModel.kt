package ua.zloydi.recipeapp.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.zloydi.recipeapp.data.repository.CategoryRepository
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.search.SearchFragmentViewModel
import ua.zloydi.recipeapp.ui.search.SearchState

class CategoriesFragmentViewModel(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val navigation: IChildNavigation
) : ViewModel(), IChildNavigation by navigation {
    private val _stateFlow: MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Empty)
    val stateFlow = _stateFlow.asStateFlow()

    class Factory(
        private val recipeRepository: RecipeRepository,
        private val categoryRepository: CategoryRepository,
        private val navigation: IChildNavigation
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchFragmentViewModel::class.java))
                return CategoriesFragmentViewModel(recipeRepository, categoryRepository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}

