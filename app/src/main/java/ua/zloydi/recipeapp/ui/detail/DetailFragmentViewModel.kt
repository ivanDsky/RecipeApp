package ua.zloydi.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.IngredientDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.ui.main.IParentNavigation

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


    suspend fun getRecipeUI(itemUI: RecipeItemUI): RecipeUI?{
        return repository.query(RecipeQuery.Recipe(itemUI.id?:return null))
            ?.toUI(itemUI)
    }

    private fun RecipeDetailDTO.toUI(itemUI: RecipeItemUI) = RecipeUI(
        title = itemUI.title,
        image = itemUI.image,
        source = source,
        description = null,
        url = url,
        ingredients = ingredients?.map { it.toUI() }?.toTypedArray(),
        calories = null,
        totalTime = itemUI.time,
        cuisineType = itemUI.types.filterIsInstance<CuisineUI>().toTypedArray(),
        mealType = itemUI.types.filterIsInstance<MealUI>().toTypedArray(),
        dishType = itemUI.types.filterIsInstance<DishUI>().toTypedArray(),
    )

    private fun IngredientDTO.toUI() = IngredientUI(
        food, text, measure, image
    )


    class Factory(private val repository: RecipeRepository, private val navigation: IParentNavigation) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DetailFragmentViewModel::class.java))
                return DetailFragmentViewModel(repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}