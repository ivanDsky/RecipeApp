package ua.zloydi.recipeapp.utils

import androidx.core.view.isVisible
import ua.zloydi.recipeapp.databinding.FragmentRecyclerViewBinding

fun FragmentRecyclerViewBinding.setLoading(isLoading: Boolean){
    rvItems.isVisible = !isLoading
    loadingView.isVisible = isLoading
}