package ua.zloydi.recipeapp.ui.core.adapterFingerprints

import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.RecipeViewHolder

class LongRecipeViewHolder(binding: LayoutLongRecipeItemBinding) :
    RecipeViewHolder<LayoutLongRecipeItemBinding, RecipeItem>(binding) {
    override fun LayoutLongRecipeItemBinding.bind(item: RecipeItem) {
        tvTitle.text = item.title
        tvDescription.text = item.description
        ivRecipePreview.setImageDrawable(item.preview)
    }
}