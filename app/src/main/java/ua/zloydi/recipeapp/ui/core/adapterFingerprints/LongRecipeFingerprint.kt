package ua.zloydi.recipeapp.ui.core.adapterFingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.RecipeFingerprint


class LongRecipeFingerprint : RecipeFingerprint<LayoutLongRecipeItemBinding, RecipeItem>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = LongRecipeViewHolder(LayoutLongRecipeItemBinding.inflate(inflater, parent, false))

    override fun compareItem(item: ua.zloydi.recipeapp.ui.core.adapter.RecipeItem) =
        item is RecipeItem

    override fun getViewType() = R.layout.layout_long_recipe_item
}