package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI

class LabelAdapter(fingerprints: List<LabelFingerprint>) :
    BaseAdapter<FilterTypeUI>(fingerprints as List<BaseFingerprint<*, FilterTypeUI>>) {
    override val Diff: DiffUtil.ItemCallback<FilterTypeUI> = LabelDiff()

    private class LabelDiff : DiffUtil.ItemCallback<FilterTypeUI>(){
        override fun areItemsTheSame(oldItem: FilterTypeUI, newItem: FilterTypeUI) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: FilterTypeUI, newItem: FilterTypeUI) = oldItem == newItem
    }
}

abstract class LabelFingerprint : BaseFingerprint<LayoutLabelBinding, FilterTypeUI>() {
    abstract fun inflate(binding: LayoutLabelBinding) : LabelViewHolder

    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = inflate(LayoutLabelBinding.inflate(inflater, parent, false))
}

abstract class LabelViewHolder(binding: LayoutLabelBinding) :
    BaseViewHolder<LayoutLabelBinding, FilterTypeUI>(binding){
    override fun bind(item: FilterTypeUI) = with(binding){
        root.text = item.name
    }
}