package ua.zloydi.recipeapp.ui.search

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.RecipeItem

class SearchFragmentViewModel : ViewModel(){
    fun getItems(res: Resources) = listOf(
        RecipeItem("Title1", res.getString(R.string.app_name), null, 0),
        RecipeItem("Title2", res.getString(R.string.app_name), null, 1),
        RecipeItem("Title3", res.getString(R.string.app_name), null, 2),
    )
}