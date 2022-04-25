package ua.zloydi.recipeapp.ui.core.adapter.filterAdapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseManuallyNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.search.filter.FilterUI
import ua.zloydi.recipeapp.utils.getThemeColor

class FilterAdapter : BaseManuallyNotifyAdapter<FilterUI>(listOf(FilterFingerprint))

object FilterFingerprint : BaseFingerprint<LayoutLabelBinding, FilterUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup,
    ) = FilterViewHolder(LayoutLabelBinding.inflate(inflater, parent, false))

    override fun compareItem(item: FilterUI) = true

    override fun getViewType() = R.layout.layout_label
}

class FilterViewHolder(binding: LayoutLabelBinding) :
    BaseViewHolder<LayoutLabelBinding, FilterUI>(binding){
    override fun bind(item: FilterUI): Unit = with(binding.root){
        text = item.name
        val color = context.getThemeColor(
            if (item.isSelected) com.google.android.material.R.attr.colorSecondary
            else com.google.android.material.R.attr.colorSecondaryVariant
        )
        backgroundTintList = ColorStateList.valueOf(color)
        setOnClickListener { item.onClick() }
    }

    override fun bind(item: FilterUI, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()){
            val color = binding.root.context.getThemeColor(
                if (item.isSelected) com.google.android.material.R.attr.colorSecondary
                else com.google.android.material.R.attr.colorSecondaryVariant
            )
            binding.root.backgroundTintList = ColorStateList.valueOf(color)
        } else super.bind(item, payloads)
    }
}