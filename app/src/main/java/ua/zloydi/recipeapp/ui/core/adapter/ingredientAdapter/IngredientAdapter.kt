package ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter

import androidx.recyclerview.widget.DiffUtil
import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

class IngredientAdapter :
    BaseAdapter<IngredientUI>(listOf(IngredientFingerprint) as List<BaseFingerprint<*, IngredientUI>>) {
    override val Diff: DiffUtil.ItemCallback<IngredientUI> = IngredientDiff()

    private class IngredientDiff : DiffUtil.ItemCallback<IngredientUI>(){
        override fun areItemsTheSame(oldItem: IngredientUI, newItem: IngredientUI) =
            oldItem.food == newItem.food

        override fun areContentsTheSame(oldItem: IngredientUI, newItem: IngredientUI) = oldItem == newItem
    }
}