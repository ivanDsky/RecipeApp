package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.recyclerview.widget.DiffUtil
import ua.zloydi.recipeapp.data.ui.RecipeItemUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

class RecipeAdapter(fingerprints: List<RecipeFingerprint<*, RecipeItemUI>>) :
    BaseAdapter<RecipeItemUI>(fingerprints as List<BaseFingerprint<*, RecipeItemUI>>) {
    override val Diff: DiffUtil.ItemCallback<RecipeItemUI> = RecipeDiff()

    private class RecipeDiff : DiffUtil.ItemCallback<RecipeItemUI>(){
        override fun areItemsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) =
            oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeItemUI, newItem: RecipeItemUI) = oldItem == newItem
    }
}