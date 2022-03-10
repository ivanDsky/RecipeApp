package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

class RecipeAdapter(fingerprints: List<RecipeFingerprint<*, RecipeUI>>) : BaseAdapter<RecipeUI>(fingerprints as List<BaseFingerprint<*, RecipeUI>>){
    override val differ = AsyncListDiffer(this, Diff())

    class Diff : DiffUtil.ItemCallback<RecipeUI>(){
        override fun areItemsTheSame(oldItem: RecipeUI, newItem: RecipeUI) = oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: RecipeUI, newItem: RecipeUI) = oldItem == newItem
    }
}