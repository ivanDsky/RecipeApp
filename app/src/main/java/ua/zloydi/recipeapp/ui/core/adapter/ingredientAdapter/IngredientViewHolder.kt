package ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter

import android.widget.TextView
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.databinding.LayoutIngredientBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.utils.firstCaps

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

