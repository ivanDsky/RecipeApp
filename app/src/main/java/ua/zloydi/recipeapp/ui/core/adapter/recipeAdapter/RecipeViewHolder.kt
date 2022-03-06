package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.ui.RecipeUI


abstract class RecipeViewHolder<VB : ViewBinding, in RI : RecipeUI>(private val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RI) = binding.bind(item)
    protected abstract fun VB.bind(item: RI)
}