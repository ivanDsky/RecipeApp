package ua.zloydi.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.main.IParentNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class DetailFragmentViewModel(
    recipe: RecipeItemDTO,
    private val repository: RecipeRepository,
    private val navigation: IParentNavigation,
) : ViewModel(), IParentNavigation by navigation {
    val recipeUI = viewModelScope.async(Dispatchers.IO){
        repository.query(
            RecipeQuery.Recipe(recipe.id ?: return@async null)
        )?.toUI(recipe)
    }

    class Factory(
        private val recipe: RecipeItemDTO,
        private val repository: RecipeRepository,
        private val navigation: IParentNavigation,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java))
                return DetailFragmentViewModel(recipe, repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}