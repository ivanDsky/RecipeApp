package ua.zloydi.recipeapp.ui.core.adapterFingerprints.label

import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI

object DishFingerprint : LabelFingerprint(){
    override fun inflate(binding: LayoutLabelBinding) = DishViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is DishUI

    override fun getViewType() = 0
}