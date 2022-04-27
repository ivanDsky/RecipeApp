package ua.zloydi.recipeapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.filter_types.SearchFilter

@Entity(tableName = "query")
data class CacheQuery(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val searchFilter: String,
    @ColumnInfo(name = "query") val queryDTO: QueryDTO,
)

fun SearchFilter.toCache(hash: String? = null, queryDTO: QueryDTO) =
    CacheQuery(hash ?: toString(), queryDTO)
