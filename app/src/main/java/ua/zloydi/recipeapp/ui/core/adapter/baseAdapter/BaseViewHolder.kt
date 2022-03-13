package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<VB : ViewBinding, IM>(protected val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: IM)
}