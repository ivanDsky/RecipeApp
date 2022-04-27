package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseFingerprint<out VB : ViewBinding,IM> {
    abstract fun inflate(inflater: LayoutInflater, parent: ViewGroup): BaseViewHolder<VB, IM>
    abstract fun compareItem(item: IM): Boolean
    abstract fun getViewType(): Int
}

abstract class BaseStaticFingerprint<out VB : ViewBinding, IM> : BaseFingerprint<VB, IM>() {
    val viewType = System.nanoTime()
    override fun compareItem(item: IM) = true
    override fun getViewType() = viewType.toInt()
}