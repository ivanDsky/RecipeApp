package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import androidx.recyclerview.widget.DiffUtil
import ua.zloydi.recipeapp.data.ui.filterType.FilterTypeUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

class LabelAdapter(fingerprints: List<LabelFingerprint>) :
    BaseAdapter<FilterTypeUI>(fingerprints as List<BaseFingerprint<*, FilterTypeUI>>) {
    override val Diff: DiffUtil.ItemCallback<FilterTypeUI> = LabelDiff()

    private class LabelDiff : DiffUtil.ItemCallback<FilterTypeUI>(){
        override fun areItemsTheSame(oldItem: FilterTypeUI, newItem: FilterTypeUI) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: FilterTypeUI, newItem: FilterTypeUI) = oldItem == newItem
    }
}