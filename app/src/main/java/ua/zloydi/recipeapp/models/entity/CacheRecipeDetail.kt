package ua.zloydi.recipeapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO

@Entity(tableName = "recipeDetail")
data class CacheRecipeDetail(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val id: String,
    @ColumnInfo(name = "recipeDetail") val recipeDetailDTO: RecipeDetailDTO,
)

fun RecipeDetailDTO.toCache(id: String) = CacheRecipeDetail(id, this)