package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.dto.RecipeDTO
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

private fun RecipeDTO.toRecipeItemUI() = RecipeItemUI(
    label, image, totalTime, emptyList()
)

class RecipePagerAdapter(
    private val fingerprints: List<RecipeFingerprint<*, RecipeItemUI>>,
    private val itemClickListener: BaseAdapter.OnItemClickListener? = null
) : PagingDataAdapter<RecipeDTO, RecipeViewHolder<ViewBinding, RecipeItemUI>>(RecipeDiff()){
    override fun onBindViewHolder(
        holder: RecipeViewHolder<ViewBinding, RecipeItemUI>,
        position: Int
    ) {
        holder.bind(getItem(position)?.toRecipeItemUI() ?: return)
        if (itemClickListener != null)
            holder.binding.root.setOnClickListener {
                itemClickListener.onItemClick(
                    holder.binding,
                    position
                )
            }
    }
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)?.toRecipeItemUI() ?: throw IllegalArgumentException("No item at position: $position")
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

    private class RecipeDiff : DiffUtil.ItemCallback<RecipeDTO>() {
        override fun areItemsTheSame(oldItem: RecipeDTO, newItem: RecipeDTO) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeDTO, newItem: RecipeDTO) =
            oldItem == newItem
    }
}