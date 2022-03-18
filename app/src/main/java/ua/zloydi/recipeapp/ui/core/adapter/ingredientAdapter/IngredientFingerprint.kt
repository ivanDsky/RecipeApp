package ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.databinding.LayoutIngredientBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

object IngredientFingerprint : BaseFingerprint<LayoutIngredientBinding, IngredientUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = IngredientViewHolder(LayoutIngredientBinding.inflate(inflater, parent, false))

    override fun compareItem(item: IngredientUI) = true

    override fun getViewType() = R.layout.layout_ingredient
}