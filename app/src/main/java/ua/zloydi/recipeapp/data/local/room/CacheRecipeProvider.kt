package ua.zloydi.recipeapp.data.local.room

import androidx.room.Room
import ua.zloydi.recipeapp.App

object CacheRecipeProvider {
    val database =
        Room.databaseBuilder(App.instance, CacheRecipeDatabase::class.java, "CacheRecipeDatabase")
            .build()
}