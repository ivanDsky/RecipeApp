package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

abstract class LabelFingerprint : BaseFingerprint<LayoutLabelBinding, FilterTypeUI>() {
    abstract fun inflate(binding: LayoutLabelBinding) : LabelViewHolder

    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = inflate(LayoutLabelBinding.inflate(inflater, parent, false))
}