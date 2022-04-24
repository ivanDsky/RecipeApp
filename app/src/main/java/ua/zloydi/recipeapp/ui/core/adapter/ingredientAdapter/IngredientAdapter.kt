package ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutIngredientBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.utils.firstCaps

class IngredientAdapter :
    BaseAdapter<IngredientUI>(listOf(IngredientFingerprint) as List<BaseFingerprint<*, IngredientUI>>) {
    override val Diff: DiffUtil.ItemCallback<IngredientUI> = IngredientDiff()

    private class IngredientDiff : DiffUtil.ItemCallback<IngredientUI>(){
        override fun areItemsTheSame(oldItem: IngredientUI, newItem: IngredientUI) =
            oldItem.food == newItem.food

        override fun areContentsTheSame(oldItem: IngredientUI, newItem: IngredientUI) = oldItem.text == newItem.text
    }
}

object IngredientFingerprint : BaseFingerprint<LayoutIngredientBinding, IngredientUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = IngredientViewHolder(LayoutIngredientBinding.inflate(inflater, parent, false))

    override fun compareItem(item: IngredientUI) = true

    override fun getViewType() = R.layout.layout_ingredient
}

class IngredientViewHolder(binding: LayoutIngredientBinding) :
    BaseViewHolder<LayoutIngredientBinding, IngredientUI>(binding){
    override fun bind(item: IngredientUI): Unit = with(binding){
        setText(tvTitle, item.food?.firstCaps())
        setText(tvDescription, item.text)
        setText(tvMeasure, item.measure)

        Glide.with(ivIngredient)
            .load(item.image)
            .into(ivIngredient)
    }

    private fun setText(tv: TextView, text: String?){
        tv.isGone = text.isNullOrBlank() || text == "<unit>"
        tv.text = text
    }
}