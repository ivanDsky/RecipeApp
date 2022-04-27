package ua.zloydi.recipeapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.filter_types.SearchFilter

@Entity(tableName = "query")
data class CacheQuery(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val searchFilter: String,
    @ColumnInfo(name = "query") val queryDTO: QueryDTO,
)

@Entity(tableName = "recipe")
data class CacheRecipe(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val id: String,
    @ColumnInfo(name = "recipe") val recipeDetailDTO: RecipeDetailDTO,
)

fun SearchFilter.toCache(hash: String? = null, queryDTO: QueryDTO) =
    CacheQuery(hash ?: toString(), queryDTO)

fun RecipeDetailDTO.toCache(id: String) = CacheRecipe(id, this)