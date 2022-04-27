package ua.zloydi.recipeapp

import android.app.Application
import ua.zloydi.recipeapp.data.local.cache.CacheProvider
import ua.zloydi.recipeapp.models.filterTypes.Cuisine
import ua.zloydi.recipeapp.models.filterTypes.Dish
import ua.zloydi.recipeapp.models.filterTypes.Meal

class App : Application(){
    companion object{
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        filterTypeInitialization()
    }

    private fun filterTypeInitialization(){
        Dish.values
        Meal.values
        Cuisine.values
        CacheProvider.database
    }

}