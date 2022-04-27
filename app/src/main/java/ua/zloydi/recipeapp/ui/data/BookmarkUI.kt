package ua.zloydi.recipeapp.ui.data

data class BookmarkUI(
    val recipeItemUI: RecipeItemUI,
    val onBookmarkClick: () -> Unit
)
