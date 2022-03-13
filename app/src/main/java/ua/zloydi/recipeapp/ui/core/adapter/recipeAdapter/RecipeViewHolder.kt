package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.ui.RecipeItemUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder


abstract class RecipeViewHolder<VB : ViewBinding, RI : RecipeItemUI>(binding: VB) :
    BaseViewHolder<VB, RI>(binding)