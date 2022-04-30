package ua.zloydi.recipeapp.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarkDatabase
import ua.zloydi.recipeapp.data.local.bookmarks.insert
import ua.zloydi.recipeapp.data.local.cache.CacheDatabase
import ua.zloydi.recipeapp.data.local.cache.insert
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filterTypes.*
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.ui.main.IParentNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DetailFragmentViewModel(
    private val id: String,
    private var _recipe: RecipeItemDTO?,
    private val cacheDatabase: CacheDatabase,
    private val bookmarkDatabase: BookmarkDatabase,
    private val repository: RecipeRepository,
    private val navigation: IParentNavigation,
) : ViewModel(), IParentNavigation by navigation {
    val isBookmarked = bookmarkDatabase.bookmark().isBookmarked(id)
    val recipe: RecipeItemDTO? get() = _recipe

    val recipeUI = viewModelScope.async(Dispatchers.IO){
        val detailRecipe: RecipeDetailDTO
        if (_recipe == null) {
            val fullRecipe = repository.query(
                RecipeQuery.RecipeItem(id)
            ) ?: return@async null
            _recipe = fullRecipe.itemDTO
            cacheToDB(_recipe!!)
            detailRecipe = fullRecipe.detailDTO
        } else {
            cacheToDB(_recipe!!)
            detailRecipe = repository.query(
                RecipeQuery.Recipe(id)
            ) ?: return@async null
        }

        val categories = detailRecipe.dishType.toUI(Dish.mapper,::DishUI){ Filter(categories = it) }
        val meals = detailRecipe.mealType.toUI(Meal.mapper,::MealUI){ Filter(meals = it) }
        val cuisines = detailRecipe.cuisineType.toUI(Cuisine.mapper,::CuisineUI){ Filter(cuisines = it) }
        detailRecipe.toUI(recipe!!, categories, meals, cuisines)
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

    private fun cacheToDB(recipeItemDTO: RecipeItemDTO) = viewModelScope.launch(Dispatchers.IO){
        cacheDatabase.recipeItem().insert(recipeItemDTO)
    }

    fun changeBookmark() = viewModelScope.launch(Dispatchers.IO){
        val isBookmarked = isBookmarked.first()
        if(isBookmarked) {
            bookmarkDatabase.bookmark().delete(id)
        }else{
            bookmarkDatabase.bookmark().insert(id)
        }
    }

    private val longLink = viewModelScope.async(Dispatchers.IO){
        val prefix = "https://recipezloydi.page.link/"
        FirebaseDynamicLinks.getInstance().createDynamicLink().run {
            link = Uri.parse("$prefix?id=$id")
            domainUriPrefix = prefix
            setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            buildDynamicLink()
        }.uri
    }

    private val shortLink = viewModelScope.async(Dispatchers.IO){
        val link = longLink.await()
        suspendCoroutine<Uri?> {cont->
            Firebase.dynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
                longLink = link
            }.addOnSuccessListener {
                cont.resume(it.shortLink)
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
    }

    val shareIntent = viewModelScope.async(Dispatchers.IO){
        Intent().apply{
            action = Intent.ACTION_SEND
            type = "text/plain"
            val link = shortLink.await() ?: longLink.await()
            putExtra(Intent.EXTRA_TEXT, link.toString())
        }
    }

    class Factory(
        private val id: String,
        private val recipe: RecipeItemDTO?,
        private val cacheDatabase: CacheDatabase,
        private val bookmarkDatabase: BookmarkDatabase,
        private val repository: RecipeRepository,
        private val navigation: IParentNavigation,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailFragmentViewModel::class.java))
                return DetailFragmentViewModel(id, recipe, cacheDatabase, bookmarkDatabase, repository, navigation) as T
            else
                throw TypeCastException()
        }
    }
}