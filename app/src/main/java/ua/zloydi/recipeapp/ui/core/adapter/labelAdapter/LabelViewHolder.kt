package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder

abstract class LabelViewHolder(binding: LayoutLabelBinding) :
    BaseViewHolder<LayoutLabelBinding, FilterTypeUI>(binding){
    override fun bind(item: FilterTypeUI) = with(binding){
        root.text = item.name
    }
}

