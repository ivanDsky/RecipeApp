package ua.zloydi.recipeapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.entity.CacheRecipe
import ua.zloydi.recipeapp.models.entity.toCache

@Dao
interface CacheRecipeDAO {
    @Query("SELECT recipe FROM recipe WHERE key = :key")
    suspend fun query(key: String): RecipeDetailDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cacheQuery: CacheRecipe)
}

suspend fun CacheRecipeDAO.insert(id: String, recipeDetailDTO: RecipeDetailDTO) =
    insert(recipeDetailDTO.toCache(id))