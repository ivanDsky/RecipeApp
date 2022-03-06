package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.ui.RecipeUI

class RecipeAdapter(private val fingerprints: List<RecipeFingerprint<*, *>>) :
    RecyclerView.Adapter<RecipeViewHolder<ViewBinding, RecipeUI>>(){
    private val differ = AsyncListDiffer(this, Diff())

    override fun getItemViewType(position: Int): Int {
        val item = differ.currentList[position]
        return fingerprints.find { it.compareItem(item) }?.getViewType()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder<ViewBinding,RecipeUI> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getViewType() == viewType }
            ?.let { it as RecipeFingerprint<ViewBinding, RecipeUI> }
            ?.inflate(inflater, parent)
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: RecipeViewHolder<ViewBinding,RecipeUI>, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun setItems(newItems: List<RecipeUI>) {
        differ.submitList(newItems)
        notifyDataSetChanged()
    }

    class Diff : DiffUtil.ItemCallback<RecipeUI>(){
        override fun areItemsTheSame(oldItem: RecipeUI, newItem: RecipeUI) = oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeUI, newItem: RecipeUI) = oldItem == newItem
    }
}