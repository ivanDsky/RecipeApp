package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import androidx.viewbinding.ViewBinding

abstract class BaseManuallyNotifyAdapter<IM>(
    fingerprints: List<BaseFingerprint<ViewBinding, IM>>,
) : BaseAdapter<IM>(fingerprints){
    private val manualUpdater = ManualUpdater<IM>()
    override val items get() =  manualUpdater.items
    override fun setItems(items: List<IM>) { manualUpdater.setItems(items) }
}