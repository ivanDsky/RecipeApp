package ua.zloydi.recipeapp.data.local.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.entity.CacheRecipeItem
import ua.zloydi.recipeapp.models.entity.toCache

@Dao
interface CacheRecipeItemDao {
    @Query("SELECT recipeItem FROM recipeItem WHERE key = :key")
    suspend fun query(key: String): RecipeItemDTO?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cacheRecipeItem: CacheRecipeItem)

    @Query("DELETE FROM recipeItem WHERE key = :key")
    suspend fun delete(key: String)
}

suspend fun CacheRecipeItemDao.insert(recipeItemDTO: RecipeItemDTO) =
    insert(recipeItemDTO.toCache())