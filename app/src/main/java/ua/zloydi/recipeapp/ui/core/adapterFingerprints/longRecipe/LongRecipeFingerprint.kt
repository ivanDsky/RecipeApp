package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeFingerprint
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory


class LongRecipeFingerprint : RecipeFingerprint<LayoutLongRecipeItemBinding, RecipeUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): LongRecipeViewHolder {
        val binding = LayoutLongRecipeItemBinding.inflate(inflater, parent, false)
        PaddingDecoratorFactory(parent.resources).apply(binding.rvLabels, 0f, 2f, false)
        return LongRecipeViewHolder(binding)
    }

    override fun compareItem(item: RecipeUI) =
        item is RecipeUI

    override fun getViewType() = R.layout.layout_long_recipe_item
}