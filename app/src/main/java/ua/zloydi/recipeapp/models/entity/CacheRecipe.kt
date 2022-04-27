package ua.zloydi.recipeapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO

@Entity(tableName = "recipe")
data class CacheRecipe(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val id: String,
    @ColumnInfo(name = "recipe") val recipeDetailDTO: RecipeDetailDTO,
)

fun RecipeDetailDTO.toCache(id: String) = CacheRecipe(id, this)