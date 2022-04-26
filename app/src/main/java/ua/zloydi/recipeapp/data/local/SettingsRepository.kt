package ua.zloydi.recipeapp.data.local

import android.app.Activity
import android.content.Context
import androidx.core.content.edit
import ua.zloydi.recipeapp.App
import ua.zloydi.recipeapp.models.filter_types.*

private const val SETTINGS = "SETTINGS"
private const val QUERY = "QUERY"
private const val CATEGORIES = "CATEGORIES"
private const val MEALS = "MEALS"
private const val CUISINES = "CUISINES"

class SettingsRepository(context: Context) {
    private val preferences = context.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE)

    fun putSearchFilter(searchFilter: SearchFilter) = preferences.edit{
        putString(QUERY, searchFilter.search)
        putStringSet(CATEGORIES, searchFilter.filter.categories.toStringSet())
        putStringSet(MEALS, searchFilter.filter.meals.toStringSet())
        putStringSet(CUISINES, searchFilter.filter.cuisines.toStringSet())
    }

    fun getSearchFilter() : SearchFilter? = with(preferences){
        val query = getString(QUERY, null) ?: return@with null
        val categories = getStringSet(CATEGORIES, null)!!.toTypedList(Dish.mapper)
        val meals = getStringSet(MEALS, null)!!.toTypedList(Meal.mapper)
        val cuisines = getStringSet(CUISINES, null)!!.toTypedList(Cuisine.mapper)
        SearchFilter(query, Filter(categories,meals, cuisines))
    }

    private fun<T : FilterType> List<T>.toStringSet(): Set<String> = map { it.label.lowercase() }.toSortedSet()
    private fun<T : FilterType> Set<String>.toTypedList(mapper: Mapper<T>) = map { mapper[it] }
}

object SettingsProvider{
    val repository = SettingsRepository(App.instance)
}