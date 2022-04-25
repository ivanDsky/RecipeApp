package ua.zloydi.recipeapp.ui.core.adapter.baseAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<IM>(
    private val fingerprints: List<BaseFingerprint<ViewBinding, IM>>,
) :
    RecyclerView.Adapter<BaseViewHolder<ViewBinding, IM>>(),
    AdapterUpdater<IM>,
    AdapterManagement<IM>
{
    private val fingerprintAdapterManagement =
        object : FingerprintAdapterManagement<IM>(fingerprints){
            override fun getItem(position: Int) = items[position]
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = fingerprintAdapterManagement.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, IM>, position: Int)
        = fingerprintAdapterManagement.onBindViewHolder(holder, position)

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, IM>,
        position: Int,
        payloads: MutableList<Any>
    ) = fingerprintAdapterManagement.onBindViewHolder(holder, position, payloads)

    override fun getItem(position: Int) = fingerprintAdapterManagement.getItem(position)

    override fun getItemViewType(position: Int)
        = fingerprintAdapterManagement.getItemViewType(position)

    override fun getItemCount() = items.size
}