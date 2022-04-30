package ua.zloydi.recipeapp.data.local.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.entity.CacheRecipeDetail
import ua.zloydi.recipeapp.models.entity.toCache

@Dao
interface CacheRecipeDetailDAO {
    @Query("SELECT recipeDetail FROM recipeDetail WHERE key = :key")
    suspend fun query(key: String): RecipeDetailDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cacheRecipeDetail: CacheRecipeDetail)
}

suspend fun CacheRecipeDetailDAO.insert(id: String, recipeDetailDTO: RecipeDetailDTO) =
    insert(recipeDetailDTO.toCache(id))