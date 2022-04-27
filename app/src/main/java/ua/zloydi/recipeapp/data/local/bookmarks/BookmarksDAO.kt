package ua.zloydi.recipeapp.data.local.bookmarks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ua.zloydi.recipeapp.models.entity.BookmarkEntity

@Dao
interface BookmarksDAO {
    @Query("SELECT * FROM bookmark ORDER BY timeAdded DESC")
    fun queryAll(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS(SELECT * FROM bookmark WHERE id = :id)")
    fun isBookmarked(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmark WHERE id = :id")
    suspend fun delete(id: String)
}

suspend fun BookmarksDAO.insert(id: String) =
    insert(BookmarkEntity(id, System.currentTimeMillis()))