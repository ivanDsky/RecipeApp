package ua.zloydi.recipeapp.ui.core.adapterFingerprints.label

import ua.zloydi.recipeapp.data.ui.filterType.FilterTypeUI
import ua.zloydi.recipeapp.data.ui.filterType.MealUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint

object MealFingerprint : LabelFingerprint() {
    override fun inflate(binding: LayoutLabelBinding) = MealViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is MealUI

    override fun getViewType() = 2
}