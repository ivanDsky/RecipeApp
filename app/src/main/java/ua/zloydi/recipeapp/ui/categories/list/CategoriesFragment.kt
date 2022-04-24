package ua.zloydi.recipeapp.ui.categories.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.data.repository.CategoryProvider
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentCategoriesBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.categoryAdapter.CategoryAdapter
import ua.zloydi.recipeapp.ui.core.adapter.categoryAdapter.CategoryFingerprint
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.main.MainFragment
import kotlin.properties.Delegates

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(){
    private val viewModel: CategoriesFragmentViewModel by viewModels {
        CategoriesFragmentViewModel.Factory(
            RecipeProvider.repository,
            CategoryProvider.repository,
            (parentFragment as MainFragment).childNavigation
        )
    }
    override fun inflate(inflater: LayoutInflater) = FragmentCategoriesBinding.inflate(inflater)

    private var adapter: CategoryAdapter by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindStable()
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect (::bindState)
        }
    }

    private fun bindStable() = with(binding){
        adapter = CategoryAdapter(CategoryFingerprint())
        rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        rvCategories.adapter = adapter
        PaddingDecoratorFactory(resources).apply(rvCategories,4f,8f)
    }

    private fun bindState(state: CategoryState) {
        if (state is CategoryState.Categories) bindCategories(state)
    }


    private fun bindCategories(state: CategoryState.Categories){
        adapter.setItems(state.list)
    }


}