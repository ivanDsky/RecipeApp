package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection.ROW
import com.google.android.flexbox.FlexWrap.WRAP
import com.google.android.flexbox.FlexboxLayoutManager
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeFingerprint
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterDecorators.SpaceDecorator


class LongRecipeFingerprint : RecipeFingerprint<LayoutLongRecipeItemBinding, RecipeUI>() {
    override fun inflate(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): LongRecipeViewHolder {
        val binding = LayoutLongRecipeItemBinding.inflate(inflater, parent, false)
        binding.rvIngredients.layoutManager =
            LinearLayoutManager(parent.context, LinearLayoutManager.VERTICAL, false)
        binding.rvLabels.layoutManager = FlexboxLayoutManager(parent.context,ROW,WRAP)
        binding.rvLabels.addItemDecoration(PaddingDecoratorFactory(parent.resources).create(0f,2f,8f,2f))
        return LongRecipeViewHolder(binding)
    }

    override fun compareItem(item: RecipeUI) =
        item is RecipeUI

    override fun getViewType() = R.layout.layout_long_recipe_item
}