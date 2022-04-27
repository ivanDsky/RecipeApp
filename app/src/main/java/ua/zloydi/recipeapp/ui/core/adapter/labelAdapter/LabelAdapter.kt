package ua.zloydi.recipeapp.ui.core.adapter.labelAdapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseDifferNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseStaticFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.utils.getThemeColor

class LabelAdapter(fingerprints: List<LabelFingerprint>) :
    BaseDifferNotifyAdapter<FilterTypeUI>(fingerprints as List<BaseFingerprint<*, FilterTypeUI>>) {
    override val diff: DiffUtil.ItemCallback<FilterTypeUI> = LabelDiff()

    private class LabelDiff : DiffUtil.ItemCallback<FilterTypeUI>(){
        override fun areItemsTheSame(oldItem: FilterTypeUI, newItem: FilterTypeUI) =
            oldItem.name == newItem.name

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FilterTypeUI, newItem: FilterTypeUI) = oldItem == newItem
    }
}

abstract class LabelFingerprint : BaseStaticFingerprint<LayoutLabelBinding, FilterTypeUI>() {
    abstract fun inflate(binding: LayoutLabelBinding) : LabelViewHolder

    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = inflate(LayoutLabelBinding.inflate(inflater, parent, false))
}

abstract class LabelViewHolder(binding: LayoutLabelBinding) :
    BaseViewHolder<LayoutLabelBinding, FilterTypeUI>(binding){
    override fun bind(item: FilterTypeUI) = with(binding){
        root.text = item.name
        root.setOnClickListener { item.onClick() }
    }
}

object CuisineFingerprint : LabelFingerprint(){
    override fun inflate(binding: LayoutLabelBinding) = CuisineViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is CuisineUI
}

class CuisineViewHolder(binding: LayoutLabelBinding) : LabelViewHolder(binding) {
    override fun bind(item: FilterTypeUI) = with(binding) {
        super.bind(item)
        root.backgroundTintList =
            ColorStateList.valueOf(root.context.getThemeColor(R.attr.colorSecondaryTone1))
    }
}

object DishFingerprint : LabelFingerprint(){
    override fun inflate(binding: LayoutLabelBinding) = DishViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is DishUI
}

class DishViewHolder(binding: LayoutLabelBinding) : LabelViewHolder(binding){
    override fun bind(item: FilterTypeUI) = with(binding){
        super.bind(item)
        root.backgroundTintList =
            ColorStateList.valueOf(root.context.getThemeColor(R.attr.colorSecondaryTone3))
    }
}

object MealFingerprint : LabelFingerprint() {
    override fun inflate(binding: LayoutLabelBinding) = MealViewHolder(binding)

    override fun compareItem(item: FilterTypeUI) = item is MealUI
}

class MealViewHolder(binding: LayoutLabelBinding) : LabelViewHolder(binding){
    override fun bind(item: FilterTypeUI) = with(binding){
        super.bind(item)
        root.backgroundTintList =
            ColorStateList.valueOf(root.context.getThemeColor(R.attr.colorSecondaryTone2))
    }
}