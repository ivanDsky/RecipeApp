package ua.zloydi.recipeapp.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.zloydi.recipeapp.models.entity.CacheQuery
import ua.zloydi.recipeapp.models.entity.CacheRecipe

@Database(entities = [CacheQuery::class, CacheRecipe::class], version = 1, exportSchema = false)
@TypeConverters(QueryDTOConverter::class,RecipeDetailDTOConverter::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun query(): CacheQueryDAO
    abstract fun recipe(): CacheRecipeDAO
}