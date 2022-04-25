package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<out VB : ViewBinding, IM>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: IM)
    open fun bind(item: IM, payloads: MutableList<Any>) = bind(item)
}