package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import ua.zloydi.recipeapp.data.ui.FilterTypeUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder

class LabelViewHolder(binding: LayoutLabelBinding) :
    BaseViewHolder<LayoutLabelBinding, FilterTypeUI>(binding){
    override fun LayoutLabelBinding.bind(item: FilterTypeUI) {
        root.text = item.name
    }
}