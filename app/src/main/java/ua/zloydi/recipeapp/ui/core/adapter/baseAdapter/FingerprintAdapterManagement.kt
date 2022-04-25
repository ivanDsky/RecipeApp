package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface AdapterManagement<IM> {
    fun getItemViewType(position: Int): Int

    fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, IM>

    fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, IM>, position: Int)

    fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, IM>,
        position: Int,
        payloads: MutableList<Any>
    ){
        onBindViewHolder(holder, position)
    }

    fun getItem(position: Int): IM
}

abstract class FingerprintAdapterManagement<IM>(
    private val fingerprints: List<BaseFingerprint<ViewBinding, IM>>
) : AdapterManagement<IM>{
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return fingerprints.find { it.compareItem(item) }?.getViewType()
            ?: throw IllegalArgumentException("View type not found: $item")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, IM> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getViewType() == viewType }
            ?.inflate(inflater, parent)
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, IM>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, IM>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.bind(getItem(position), payloads)
    }
}