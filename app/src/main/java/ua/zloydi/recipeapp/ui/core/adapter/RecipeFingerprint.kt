package ua.zloydi.recipeapp.ui.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class RecipeFingerprint<VB : ViewBinding, RI : RecipeItem>{
    abstract fun inflate(inflater: LayoutInflater, parent: ViewGroup) : RecipeViewHolder<VB, RI>
    abstract fun compareItem(item: RecipeItem) : Boolean
    abstract fun getViewType() : Int
}