package ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutIngredientBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseDifferNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseStaticFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.utils.firstCaps

class IngredientAdapter :
    BaseDifferNotifyAdapter<IngredientUI>(listOf(IngredientFingerprint) as List<BaseFingerprint<*, IngredientUI>>) {
    override val diff: DiffUtil.ItemCallback<IngredientUI> = IngredientDiff()

    private class IngredientDiff : DiffUtil.ItemCallback<IngredientUI>(){
        override fun areItemsTheSame(oldItem: IngredientUI, newItem: IngredientUI) =
            oldItem.food == newItem.food

        override fun areContentsTheSame(oldItem: IngredientUI, newItem: IngredientUI) = oldItem.text == newItem.text
    }
}

object IngredientFingerprint : BaseStaticFingerprint<LayoutIngredientBinding, IngredientUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = IngredientViewHolder(LayoutIngredientBinding.inflate(inflater, parent, false))
}

class IngredientViewHolder(binding: LayoutIngredientBinding) :
    BaseViewHolder<LayoutIngredientBinding, IngredientUI>(binding){
    override fun bind(item: IngredientUI): Unit = with(binding){
        setText(tvTitle, item.food?.firstCaps())
        setText(tvDescription, item.text)
        setText(tvMeasure, getMeasureText(item.measure, item.quantity, item.weight))

        Glide.with(ivIngredient)
            .load(item.image)
            .placeholder(R.drawable.logo_transparent)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(ivIngredient)
    }

    private fun getMeasureText(measure: String?, quantity: Float?, weight: Float?) = buildString {
        val isMeasureValid = !measure.isNullOrEmpty() && measure != "<unit>" && measure != "gram"
        if (isMeasureValid && quantity != null) {
            val format = if (quantity >= 1f) "%.0f " else "%.1f "
            append(String.format(format, quantity))
        }
        if (isMeasureValid) append(measure)
        if (weight != null) {
            if (isMeasureValid) append(" or ")
            append(String.format("≈%.0f", weight))
            append('g')
        }
    }

    private fun setText(tv: TextView, text: String?){
        tv.isGone = text.isNullOrBlank()
        tv.text = text
    }
}