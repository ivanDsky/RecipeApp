package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeViewHolder
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.CuisineFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.DishFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.MealFingerprint

class LongRecipeViewHolder(binding: LayoutLongRecipeItemBinding) :
    RecipeViewHolder<LayoutLongRecipeItemBinding, RecipeUI>(binding) {
    override fun bind(item: RecipeUI) = with(binding){
        tvTitle.text = item.title

        Glide.with(ivRecipePreview)
            .load(item.image)
            .into(ivRecipePreview)

        rvLabels.adapter = LabelAdapter(listOf(CuisineFingerprint(), DishFingerprint(), MealFingerprint()))
            .also { it.setItems(item.types)}
    }
}