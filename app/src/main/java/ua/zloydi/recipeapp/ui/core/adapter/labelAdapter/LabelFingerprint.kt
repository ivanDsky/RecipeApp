package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.ui.FilterTypeUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder

class LabelFingerprint : BaseFingerprint<LayoutLabelBinding, FilterTypeUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<LayoutLabelBinding, FilterTypeUI> {
        return LabelViewHolder(LayoutLabelBinding.inflate(inflater, parent, false))
    }

    override fun compareItem(item: FilterTypeUI) = true

    override fun getViewType() = R.layout.layout_label
}