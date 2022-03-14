package ua.zloydi.recipeapp.ui.detail

import androidx.lifecycle.ViewModel
import ua.zloydi.recipeapp.data.ui.RecipeUI

class DetailFragmentViewModel : ViewModel(){
    private var _recipe: RecipeUI? = null
    var recipe: RecipeUI
        get() = if (_recipe == null) throw UninitializedPropertyAccessException() else _recipe!!
        set(value) {
            if (_recipe != null) return else _recipe = value
        }

}