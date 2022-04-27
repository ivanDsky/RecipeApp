package ua.zloydi.recipeapp.ui.core.adapter.categoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.databinding.LayoutCategoryItemBinding
import ua.zloydi.recipeapp.ui.categories.list.CategoryUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseDifferNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseStaticFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder

class CategoryAdapter(fingerprint: CategoryFingerprint) :
    BaseDifferNotifyAdapter<CategoryUI>(listOf(fingerprint)) {
    override val diff: DiffUtil.ItemCallback<CategoryUI> = CategoryDiff()

    private class CategoryDiff : DiffUtil.ItemCallback<CategoryUI>(){
        override fun areItemsTheSame(oldItem: CategoryUI, newItem: CategoryUI) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: CategoryUI, newItem: CategoryUI) = oldItem.name == newItem.name
    }
}

object CategoryFingerprint : BaseStaticFingerprint<LayoutCategoryItemBinding, CategoryUI>() {
    override fun inflate(inflater: LayoutInflater, parent: ViewGroup) =
        CategoryViewHolder(LayoutCategoryItemBinding.inflate(inflater, parent, false))
}

class CategoryViewHolder(binding: LayoutCategoryItemBinding) :
    BaseViewHolder<LayoutCategoryItemBinding, CategoryUI>(binding){
    override fun bind(item: CategoryUI): Unit = with(binding){
        setText(tvTitle, item.name)

        Glide.with(ivRecipePreview)
            .load(item.icon)
            .into(ivRecipePreview)

        root.setOnClickListener { item.onClick() }
    }

    private fun setText(tv: TextView, text: String?){
        tv.isGone = text.isNullOrBlank() || text == "<unit>"
        tv.text = text
    }
}