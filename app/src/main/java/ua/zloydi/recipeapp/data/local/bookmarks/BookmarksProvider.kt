package ua.zloydi.recipeapp.data.local.bookmarks

import androidx.room.Room
import ua.zloydi.recipeapp.App

object BookmarksProvider {
    val database =
        Room.databaseBuilder(App.instance, BookmarkDatabase::class.java, "Bookmark")
            .build()
}