package ua.zloydi.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.main.IParentNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class DetailFragmentViewModel (
    private val repository: RecipeRepository,
    private val navigation: IParentNavigation
) : ViewModel(), IParentNavigation by navigation{
    private var _recipe: RecipeUI? = null
    var recipe: RecipeUI
        get() = if (_recipe == null) throw UninitializedPropertyAccessException() else _recipe!!
        set(value) {
            if (_recipe != null) return else _recipe = value
        }


    suspend fun getRecipeUI(itemUI: RecipeItemDTO): RecipeUI?{
        return repository.query(RecipeQuery.Recipe(itemUI.id?:return null))
            ?.toUI(itemUI)
    }

    class Factory(private val repository: RecipeRepository, private val navigation: IParentNavigation) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DetailFragmentViewModel::class.java))
                return DetailFragmentViewModel(repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}