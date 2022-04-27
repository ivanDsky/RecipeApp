package ua.zloydi.recipeapp.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class BookmarkEntity (
    @PrimaryKey
    val id: String,
    val timeAdded: Long,
)