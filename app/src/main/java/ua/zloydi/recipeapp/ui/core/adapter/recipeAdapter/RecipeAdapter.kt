package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseDifferNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.RecipeItemUI

class RecipeAdapter(
    fingerprints: List<RecipeFingerprint<*, RecipeItemUI>>
) :
    BaseDifferNotifyAdapter<RecipeItemUI>(
        fingerprints as List<BaseFingerprint<*, RecipeItemUI>>,
    ) {

    override val diff: DiffUtil.ItemCallback<RecipeItemUI> = RecipeDiff()

    private class RecipeDiff : DiffUtil.ItemCallback<RecipeItemUI>() {
        override fun areItemsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem == newItem
    }
}

abstract class RecipeFingerprint<out VB : ViewBinding, RI : RecipeItemUI> : BaseFingerprint<VB, RI>()
abstract class RecipeViewHolder<out VB : ViewBinding, RI : RecipeItemUI>(binding: VB) :
    BaseViewHolder<VB, RI>(binding)