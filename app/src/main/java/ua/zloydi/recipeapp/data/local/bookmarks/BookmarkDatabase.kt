package ua.zloydi.recipeapp.data.local.bookmarks

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.zloydi.recipeapp.models.entity.BookmarkEntity
import ua.zloydi.recipeapp.models.entity.BookmarkRecipeItem

@Database(entities = [BookmarkEntity::class, BookmarkRecipeItem::class], version = 1, exportSchema = false)
@TypeConverters(RecipeItemDTOConverter::class)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmark(): BookmarksDAO
    abstract fun recipeItem(): BookmarkRecipeItemDao
}