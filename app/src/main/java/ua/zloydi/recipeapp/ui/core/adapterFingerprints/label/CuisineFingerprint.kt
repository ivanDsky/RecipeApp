package ua.zloydi.recipeapp.ui.core.adapterFingerprints.label

import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelFingerprint
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI

object CuisineFingerprint : LabelFingerprint(){
    override fun inflate(binding: LayoutLabelBinding) = CuisineViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is CuisineUI

    override fun getViewType() = 2
}

