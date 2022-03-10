package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, IM>(private val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: IM) = binding.bind(item)
    protected abstract fun VB.bind(item: IM)
}