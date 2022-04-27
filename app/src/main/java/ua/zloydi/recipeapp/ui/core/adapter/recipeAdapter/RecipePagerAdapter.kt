package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseStaticFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.FingerprintAdapterManagement
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

class RecipePagerAdapter(
    private val fingerprints: List<BaseFingerprint<ViewBinding, RecipeItemUI>>,
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

class RecipeFingerprint : BaseStaticFingerprint<LayoutRecipeItemBinding, RecipeItemUI>() {
    override fun inflate(inflater: LayoutInflater, parent: ViewGroup) =
        RecipeItemViewHolder(LayoutRecipeItemBinding.inflate(inflater, parent, false))
}

class RecipeItemViewHolder(binding: LayoutRecipeItemBinding) :
    BaseViewHolder<LayoutRecipeItemBinding, RecipeItemUI>(binding) {

    override fun bind(item: RecipeItemUI): Unit = with(binding){
        ivRecipePreview.transitionName = "ivRecipePreview$adapterPosition"
        tvTitle.text = item.title
        if(item.time == null || item.time < 1 || item.time > 240) {
            tvTime.isVisible = false
        }else{
            tvTime.isVisible = true
            tvTime.text = root.resources.getString(R.string.time, item.time)
        }

        root.setOnClickListener { item.onClick() }

        Glide.with(ivRecipePreview)
            .load(item.image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(ivRecipePreview)
    }
}