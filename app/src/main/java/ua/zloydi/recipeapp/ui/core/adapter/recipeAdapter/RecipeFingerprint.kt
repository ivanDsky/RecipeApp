package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import androidx.viewbinding.ViewBinding
import ua.zloydi.recipeapp.data.ui.RecipeItemUI
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint

abstract class RecipeFingerprint<VB : ViewBinding, RI : RecipeItemUI> : BaseFingerprint<VB, RI>()