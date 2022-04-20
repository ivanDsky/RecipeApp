package ua.zloydi.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.data.repository.CategoryProvider
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentCategoriesBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.main.MainFragment

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(){
    private val viewModel: CategoriesFragmentViewModel by viewModels {
        CategoriesFragmentViewModel.Factory(
            RecipeProvider.repository,
            CategoryProvider.repository,
            (parentFragment as MainFragment).childNavigation
        )
    }
    override fun inflate(inflater: LayoutInflater) = FragmentCategoriesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect {

            }
        }
    }

    private fun bindState(){

    }


}