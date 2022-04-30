package ua.zloydi.recipeapp.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.zloydi.recipeapp.models.entity.CacheQuery
import ua.zloydi.recipeapp.models.entity.CacheRecipeDetail
import ua.zloydi.recipeapp.models.entity.CacheRecipeItem

@Database(entities = [CacheQuery::class, CacheRecipeDetail::class, CacheRecipeItem::class], version = 1, exportSchema = false)
@TypeConverters(QueryDTOConverter::class,RecipeDetailDTOConverter::class, RecipeItemDTOConverter::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun query(): CacheQueryDAO
    abstract fun recipeDetail(): CacheRecipeDetailDAO
    abstract fun recipeItem(): CacheRecipeItemDao
}