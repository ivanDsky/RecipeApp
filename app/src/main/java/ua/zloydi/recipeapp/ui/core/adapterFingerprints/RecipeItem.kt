package ua.zloydi.recipeapp.ui.core.adapterFingerprints

import android.graphics.drawable.Drawable

data class RecipeItem(
    val title: String, val description: String, val preview: Drawable, override val id: Int
) : ua.zloydi.recipeapp.ui.core.adapter.RecipeItem() {
    override fun isEquals(item: ua.zloydi.recipeapp.ui.core.adapter.RecipeItem): Boolean {
        return item is RecipeItem && item == this
    }
}