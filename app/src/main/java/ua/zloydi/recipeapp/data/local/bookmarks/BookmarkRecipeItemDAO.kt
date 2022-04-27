package ua.zloydi.recipeapp.data.local.bookmarks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.entity.BookmarkRecipeItem
import ua.zloydi.recipeapp.models.entity.toCache

@Dao
interface BookmarkRecipeItemDao {
    @Query("SELECT recipeItem FROM recipeItem WHERE key = :key")
    suspend fun query(key: String): RecipeItemDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmarkRecipeItem: BookmarkRecipeItem)

    @Query("DELETE FROM recipeItem WHERE key = :key")
    suspend fun delete(key: String)
}

suspend fun BookmarkRecipeItemDao.insert(recipeItemDTO: RecipeItemDTO) =
    insert(recipeItemDTO.toCache())