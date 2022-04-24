package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

class RecipePagerAdapter(
    private val fingerprints: List<RecipeFingerprint<*, RecipeItemUI>>,
) : PagingDataAdapter<RecipeItemUI, RecipeViewHolder<ViewBinding, RecipeItemUI>>(RecipeDiff()){
    override fun onBindViewHolder(
        holder: RecipeViewHolder<ViewBinding, RecipeItemUI>,
        position: Int
    ) {
        holder.bind(getItem(position) ?: return)
    }
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: throw IllegalArgumentException("No item at position: $position")
        return fingerprints.find { it.compareItem(item) }?.getViewType()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder<ViewBinding, RecipeItemUI> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getViewType() == viewType }
            ?.let { it.inflate(inflater, parent) as RecipeViewHolder<ViewBinding, RecipeItemUI> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    private class RecipeDiff : DiffUtil.ItemCallback<RecipeItemUI>() {
        override fun areItemsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem == newItem
    }
}