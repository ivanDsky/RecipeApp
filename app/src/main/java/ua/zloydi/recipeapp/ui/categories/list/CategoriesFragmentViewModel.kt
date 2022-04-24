package ua.zloydi.recipeapp.ui.categories.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.zloydi.recipeapp.data.repository.CategoryRepository
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.Category
import ua.zloydi.recipeapp.ui.main.IChildNavigation

sealed interface CategoryState{
    object Empty : CategoryState
    data class Categories(val list: List<CategoryUI>) : CategoryState
}

class CategoriesFragmentViewModel(
    categoryRepository: CategoryRepository,
    private val navigation: IChildNavigation
) : ViewModel(), IChildNavigation by navigation {
    private val _stateFlow: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState.Empty)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        _stateFlow.value =
            CategoryState.Categories(categoryRepository.getCategories().map { it.toUI() })
    }

    private fun openCategory(category: Category){
        navigation.openCategory(RecipeQuery.Category(category.dish))
     }

    private fun Category.toUI() = CategoryUI(
        name = name,
        icon = drawable,
        onClick = {openCategory(this)}
    )

    class Factory(
        private val recipeRepository: RecipeRepository,
        private val categoryRepository: CategoryRepository,
        private val navigation: IChildNavigation
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(CategoriesFragmentViewModel::class.java))
                return CategoriesFragmentViewModel(categoryRepository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}

