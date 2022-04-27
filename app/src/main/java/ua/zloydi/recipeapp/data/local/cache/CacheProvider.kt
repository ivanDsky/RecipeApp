package ua.zloydi.recipeapp.data.local.cache

import androidx.room.Room
import ua.zloydi.recipeapp.App

object CacheProvider {
    val database =
        Room.databaseBuilder(App.instance, CacheDatabase::class.java, "CacheDatabase")
            .build()
}