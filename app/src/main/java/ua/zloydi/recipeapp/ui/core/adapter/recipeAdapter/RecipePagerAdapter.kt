package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.FingerprintAdapterManagement
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

class RecipePagerAdapter(
    private val fingerprints: List<RecipeFingerprint<ViewBinding, RecipeItemUI>>,
) : PagingDataAdapter<RecipeItemUI, BaseViewHolder<ViewBinding, RecipeItemUI>>(RecipeDiff()){
    private val fingerprintAdapterManagement =
        object : FingerprintAdapterManagement<RecipeItemUI>(fingerprints){
            override fun getItem(position: Int) =
                this@RecipePagerAdapter.getItem(position) ?: throw IllegalArgumentException()
        }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, RecipeItemUI>,
        position: Int,
    ) = fingerprintAdapterManagement.onBindViewHolder(holder, position)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = fingerprintAdapterManagement.onCreateViewHolder(parent, viewType)

    override fun getItemViewType(position: Int)
        = fingerprintAdapterManagement.getItemViewType(position)

    private class RecipeDiff : DiffUtil.ItemCallback<RecipeItemUI>() {
        override fun areItemsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem == newItem
    }
}