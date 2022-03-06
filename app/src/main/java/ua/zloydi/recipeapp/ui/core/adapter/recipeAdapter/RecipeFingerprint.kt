package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.ui.RecipeUI

abstract class RecipeFingerprint<VB : ViewBinding, RI : RecipeUI>{
    abstract fun inflate(inflater: LayoutInflater, parent: ViewGroup) : RecipeViewHolder<VB, RI>
    abstract fun compareItem(item: RecipeUI) : Boolean
    abstract fun getViewType() : Int
}