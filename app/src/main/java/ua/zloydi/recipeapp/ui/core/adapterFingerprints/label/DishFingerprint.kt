package ua.zloydi.recipeapp.ui.core.adapterFingerprints.label

import ua.zloydi.recipeapp.data.ui.filterType.DishUI
import ua.zloydi.recipeapp.data.ui.filterType.FilterTypeUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint

object DishFingerprint : LabelFingerprint(){
    override fun inflate(binding: LayoutLabelBinding) = DishViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is DishUI

    override fun getViewType() = 1
}