package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder


abstract class RecipeViewHolder<VB : ViewBinding, RI : RecipeUI>(binding: VB) :
    BaseViewHolder<VB, RI>(binding)