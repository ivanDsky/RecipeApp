package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<IM>(private val fingerprints: List<BaseFingerprint<*, IM>>) :
    RecyclerView.Adapter<BaseViewHolder<ViewBinding, IM>>(){
    protected abstract val differ: AsyncListDiffer<IM>

    override fun getItemViewType(position: Int): Int {
        val item = differ.currentList[position]
        return fingerprints.find { it.compareItem(item) }?.getViewType()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, IM> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getViewType() == viewType }
            ?.let { it as BaseFingerprint<ViewBinding, IM> }
            ?.inflate(inflater, parent)
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, IM>, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun setItems(newItems: List<IM>) {
        differ.submitList(newItems)
        notifyDataSetChanged()
    }
}