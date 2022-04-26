package ua.zloydi.recipeapp.ui.core.adapterFingerprints.label

import android.content.res.ColorStateList
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLabelBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelViewHolder
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI
import ua.zloydi.recipeapp.utils.getThemeColor

class MealViewHolder(binding: LayoutLabelBinding) : LabelViewHolder(binding){
    override fun bind(item: FilterTypeUI) = with(binding){
        super.bind(item)
        root.backgroundTintList =
            ColorStateList.valueOf(root.context.getThemeColor(R.attr.colorSecondaryTone2))
    }
}