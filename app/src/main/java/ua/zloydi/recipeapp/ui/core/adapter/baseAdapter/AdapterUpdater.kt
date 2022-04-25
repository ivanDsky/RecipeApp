package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import kotlin.properties.Delegates

interface AdapterUpdater<IM> {
    val items: List<IM>
    fun setItems(items: List<IM>)
}

class DifferUpdater<IM>(
    private val recyclerView: BaseAdapter<IM>,
    Diff: DiffUtil.ItemCallback<IM>,
) : AdapterUpdater<IM>{
    private val differ = AsyncListDiffer(recyclerView, Diff)

    override fun setItems(items: List<IM>) {
        differ.submitList(items)
        recyclerView.notifyDataSetChanged()
    }

    override val items: List<IM> get() = differ.currentList
}

class ManualUpdater<IM> : AdapterUpdater<IM>{
    private var currentList: MutableList<IM> by Delegates.notNull()

    override val items get() = currentList

    override fun setItems(items: List<IM>) {
        currentList = items as MutableList<IM>
    }
}