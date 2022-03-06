package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.data.filter_types.cuisineMapper
import ua.zloydi.recipeapp.data.filter_types.dishMapper
import ua.zloydi.recipeapp.data.filter_types.mealMapper
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeViewHolder

class LongRecipeViewHolder(binding: LayoutLongRecipeItemBinding) :
    RecipeViewHolder<LayoutLongRecipeItemBinding, RecipeUI>(binding) {
    override fun LayoutLongRecipeItemBinding.bind(item: RecipeUI) {
        tvTitle.text = item.title

        Glide.with(ivRecipePreview)
            .load(item.image)
            .into(ivRecipePreview)

        tvMealType.text = mealMapper.string(item.mealType)
        tvCuisineType.text = cuisineMapper.string(item.cuisineType)
        tvDishType.text = dishMapper.string(item.dishType)

    }
}