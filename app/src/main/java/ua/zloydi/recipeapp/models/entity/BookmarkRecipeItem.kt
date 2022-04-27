package ua.zloydi.recipeapp.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO

@Entity(tableName = "recipeItem")
data class BookmarkRecipeItem(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val id: String,
    @ColumnInfo(name = "recipeItem") val recipeItem: RecipeItemDTO,
)

fun RecipeItemDTO.toCache() = BookmarkRecipeItem(id, this)