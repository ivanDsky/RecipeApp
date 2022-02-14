package ua.zloydi.recipeapp.ui.core.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class RecipeViewHolder<VB : ViewBinding, in RI : RecipeItem>(private val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RI) = binding.bind(item)
    protected abstract fun VB.bind(item: RI)
}