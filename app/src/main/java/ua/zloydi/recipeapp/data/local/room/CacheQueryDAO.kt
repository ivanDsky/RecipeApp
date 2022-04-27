package ua.zloydi.recipeapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.entity.CacheQuery
import ua.zloydi.recipeapp.models.entity.toCache
import ua.zloydi.recipeapp.models.filter_types.SearchFilter

@Dao
interface CacheQueryDAO {
    @Query("SELECT query FROM query WHERE key = :key")
    suspend fun query(key: String): QueryDTO?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cacheQuery: CacheQuery)
}

suspend fun CacheQueryDAO.query(searchFilter: SearchFilter, hash: String? = null) = query(hash?:searchFilter.toString())

suspend fun CacheQueryDAO.insert(searchFilter: SearchFilter, hash: String? = null, queryDTO: QueryDTO) =
    insert(searchFilter.toCache(hash, queryDTO))