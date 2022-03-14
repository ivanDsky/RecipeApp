package ua.zloydi.recipeapp.ui.core.adapterFingerprints.label

import ua.zloydi.recipeapp.data.ui.filterType.CuisineUI
import ua.zloydi.recipeapp.data.ui.filterType.FilterTypeUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint

object CuisineFingerprint : LabelFingerprint(){
    override fun inflate(binding: LayoutLabelBinding) = CuisineViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is CuisineUI

    override fun getViewType() = 0
}

