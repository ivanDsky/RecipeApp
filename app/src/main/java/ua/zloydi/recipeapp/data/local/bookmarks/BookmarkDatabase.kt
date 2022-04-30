package ua.zloydi.recipeapp.data.local.bookmarks

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.zloydi.recipeapp.models.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmark(): BookmarksDAO
}