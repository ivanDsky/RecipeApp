package ua.zloydi.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarkDatabase
import ua.zloydi.recipeapp.data.local.bookmarks.insert
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filterTypes.*
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.ui.main.IParentNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class DetailFragmentViewModel(
    val recipe: RecipeItemDTO,
    private val bookmarkDatabase: BookmarkDatabase,
    private val repository: RecipeRepository,
    private val navigation: IParentNavigation,
) : ViewModel(), IParentNavigation by navigation {
    private val id = recipe.id
    val isBookmarked = bookmarkDatabase.bookmark().isBookmarked(id)

    val recipeUI = viewModelScope.async(Dispatchers.IO){
        val detailRecipe = repository.query(
            RecipeQuery.Recipe(id)
        ) ?: return@async null
        val categories = detailRecipe.dishType.toUI(Dish.mapper,::DishUI){ Filter(categories = it) }
        val meals = detailRecipe.mealType.toUI(Meal.mapper,::MealUI){ Filter(meals = it) }
        val cuisines = detailRecipe.cuisineType.toUI(Cuisine.mapper,::CuisineUI){ Filter(cuisines = it) }
        detailRecipe.toUI(recipe, categories, meals, cuisines)
    }

    private fun <T : FilterType, V> List<String>.toUI(
        mapper: Mapper<T>,
        uiFactory: (String, () -> Unit) -> V,
        filterFactory: (List<T>) -> Filter,
    ) = flatMap { it.split('/') }
        .map {
            val item = mapper[it]
            uiFactory(item.label){
                navigation.openSearch(SearchFilter(filter = filterFactory(listOf(item))))
            }
        }

    fun changeBookmark() = viewModelScope.launch(Dispatchers.IO){
        val isBookmarked = isBookmarked.first()
        if(isBookmarked) {
            bookmarkDatabase.bookmark().delete(id)
            bookmarkDatabase.recipeItem().delete(id)
        }else{
            bookmarkDatabase.bookmark().insert(id)
            bookmarkDatabase.recipeItem().insert(recipe)
        }
    }

    class Factory(
        private val recipe: RecipeItemDTO,
        private val bookmarkDatabase: BookmarkDatabase,
        private val repository: RecipeRepository,
        private val navigation: IParentNavigation,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java))
                return DetailFragmentViewModel(recipe, bookmarkDatabase, repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}