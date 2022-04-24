package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeViewHolder
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.CuisineFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.DishFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.MealFingerprint
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

class LongRecipeViewHolder(binding: LayoutLongRecipeItemBinding) :
    RecipeViewHolder<LayoutLongRecipeItemBinding, RecipeItemUI>(binding) {
    override fun bind(item: RecipeItemUI) = with(binding){
        ivRecipePreview.transitionName = "ivRecipePreview$adapterPosition"
        tvTitle.text = item.title
        if(item.time == null || item.time < 1 || item.time > 240) {
            tvTime.isVisible = false
        }else{
            tvTime.isVisible = true
            tvTime.text = root.resources.getString(R.string.time, item.time)
        }

        root.setOnClickListener { item.onClick()}

        Glide.with(ivRecipePreview)
            .load(item.image)
            .into(ivRecipePreview)

        rvLabels.adapter = LabelAdapter(listOf(CuisineFingerprint, DishFingerprint, MealFingerprint))
            .also { it.setItems(item.types)}
    }
}