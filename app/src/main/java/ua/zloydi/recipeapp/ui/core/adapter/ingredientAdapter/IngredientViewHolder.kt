package ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter

import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.databinding.LayoutIngredientBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder

class IngredientViewHolder(binding: LayoutIngredientBinding) :
    BaseViewHolder<LayoutIngredientBinding, IngredientUI>(binding){
    override fun bind(item: IngredientUI) = with(binding){
        tvTitle.text = item.food
        tvDescription.text = item.text
        tvMeasure.text = item.measure
    }
}

