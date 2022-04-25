package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BaseDifferNotifyAdapter<IM>(
    fingerprints: List<BaseFingerprint<ViewBinding, IM>>,
) : BaseAdapter<IM>(fingerprints){
    abstract val diff: DiffUtil.ItemCallback<IM>

    private val differUpdater : DifferUpdater<IM> by lazy { DifferUpdater(this,diff)}

    override val items get() = differUpdater.items
    override fun setItems(items: List<IM>) {
        differUpdater.setItems(items)
    }
}