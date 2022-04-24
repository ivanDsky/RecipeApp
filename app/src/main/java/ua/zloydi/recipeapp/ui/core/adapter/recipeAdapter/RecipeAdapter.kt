package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

class RecipeAdapter(
    fingerprints: List<RecipeFingerprint<*, RecipeItemUI>>
) :
    BaseAdapter<RecipeItemUI>(
        fingerprints as List<BaseFingerprint<*, RecipeItemUI>>,
    ) {

    override val Diff: DiffUtil.ItemCallback<RecipeItemUI> = RecipeDiff()

    private class RecipeDiff : DiffUtil.ItemCallback<RecipeItemUI>() {
        override fun areItemsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem == newItem
    }
}

abstract class RecipeFingerprint<VB : ViewBinding, RI : RecipeItemUI> : BaseFingerprint<VB, RI>()
abstract class RecipeViewHolder<VB : ViewBinding, RI : RecipeItemUI>(binding: VB) :
    BaseViewHolder<VB, RI>(binding)