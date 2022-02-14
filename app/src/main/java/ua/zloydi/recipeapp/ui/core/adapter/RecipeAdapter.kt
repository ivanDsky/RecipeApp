package ua.zloydi.recipeapp.ui.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class RecipeAdapter(private val fingerprints: List<RecipeFingerprint<*, *>>) :
    RecyclerView.Adapter<RecipeViewHolder<ViewBinding,RecipeItem>>(){
    private val differ = AsyncListDiffer(this, Diff())

    override fun getItemViewType(position: Int): Int {
        val item = differ.currentList[position]
        return fingerprints.find { it.compareItem(item) }?.getViewType()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder<ViewBinding,RecipeItem> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getViewType() == viewType }
            ?.let { it as RecipeFingerprint<ViewBinding, RecipeItem> }
            ?.inflate(inflater, parent)
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: RecipeViewHolder<ViewBinding,RecipeItem>, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun setItems(newItems: List<RecipeItem>) {
        differ.submitList(newItems)
        notifyDataSetChanged()
    }

    class Diff : DiffUtil.ItemCallback<RecipeItem>(){
        override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem) = oldItem.isEquals(newItem)
    }
}