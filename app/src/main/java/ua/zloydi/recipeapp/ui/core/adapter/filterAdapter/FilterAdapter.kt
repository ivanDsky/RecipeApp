package ua.zloydi.recipeapp.ui.core.adapter.filterAdapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseManuallyNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseStaticFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.search.filter.FilterUI
import ua.zloydi.recipeapp.utils.getThemeColor

class FilterAdapter(fingerprint: FilterFingerprint) : BaseManuallyNotifyAdapter<FilterUI>(
    listOf(fingerprint)
)

sealed class FilterFingerprint : BaseStaticFingerprint<LayoutLabelBinding, FilterUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = inflate(LayoutLabelBinding.inflate(inflater, parent, false))
    abstract val inflate : (binding: LayoutLabelBinding) -> FilterViewHolder
    override fun compareItem(item: FilterUI) = true

    object Cuisine : FilterFingerprint(){
        override val inflate = FilterViewHolder::Cuisine
    }

    object Meal : FilterFingerprint(){
        override val inflate = FilterViewHolder::Meal
    }

    object Category : FilterFingerprint(){
        override val inflate = FilterViewHolder::Category
    }
}

sealed class FilterViewHolder(binding: LayoutLabelBinding) :
    BaseViewHolder<LayoutLabelBinding, FilterUI>(binding){
    private val selectedColor = com.google.android.material.R.attr.colorSecondary
    abstract val unselectedColor: Int
    override fun bind(item: FilterUI): Unit = with(binding.root){
        text = item.name
        val color = context.getThemeColor(
            if (item.isSelected) selectedColor else unselectedColor
        )
        backgroundTintList = ColorStateList.valueOf(color)
        setOnClickListener { item.onClick() }
    }

    override fun bind(item: FilterUI, payloads: MutableList<Any>) {
        if(payloads.isNotEmpty()){
            val color = binding.root.context.getThemeColor(
                if (item.isSelected) selectedColor else unselectedColor
            )
            binding.root.backgroundTintList = ColorStateList.valueOf(color)
        } else super.bind(item, payloads)
    }

    class Cuisine(binding: LayoutLabelBinding) : FilterViewHolder(binding){
        override val unselectedColor = R.attr.colorSecondaryTone1
    }

    class Meal(binding: LayoutLabelBinding) : FilterViewHolder(binding){
        override val unselectedColor = R.attr.colorSecondaryTone2
    }

    class Category(binding: LayoutLabelBinding) : FilterViewHolder(binding){
        override val unselectedColor = R.attr.colorSecondaryTone3
    }
}