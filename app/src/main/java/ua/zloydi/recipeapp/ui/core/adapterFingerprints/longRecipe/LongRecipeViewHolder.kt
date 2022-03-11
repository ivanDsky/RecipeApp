package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeViewHolder

class LongRecipeViewHolder(binding: LayoutLongRecipeItemBinding) :
    RecipeViewHolder<LayoutLongRecipeItemBinding, RecipeUI>(binding) {
    override fun LayoutLongRecipeItemBinding.bind(item: RecipeUI) {
        tvTitle.text = item.title

        Glide.with(ivRecipePreview)
            .load(item.image)
            .into(ivRecipePreview)

        rvLabels.adapter = LabelAdapter(listOf(LabelFingerprint()))
            .also { it.setItems(item.types)}
    }
}