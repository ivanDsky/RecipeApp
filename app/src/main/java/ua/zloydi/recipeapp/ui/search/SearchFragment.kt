package ua.zloydi.recipeapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import ua.zloydi.recipeapp.data.dto.RecipeDTO
import ua.zloydi.recipeapp.data.error.ErrorProvider
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RetrofitProvider
import ua.zloydi.recipeapp.databinding.FragmentSearchBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    private val viewModel: SearchFragmentViewModel by viewModels{
        SearchFragmentViewModel.Factory(RecipeRepository(RetrofitProvider.service, ErrorProvider.service))
    }
    override fun inflate(inflater: LayoutInflater) = FragmentSearchBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                when(it){
//                    SearchState.Empty -> TODO()
                    is SearchState.Response -> updateFlow(it.flow)
                }
            }
        }

        with(binding.layoutSearch){
            btnSearch.setOnClickListener {viewModel.query(etQuery.text?.toString())}
        }

        setupAdapter()
    }

    private fun updateFlow(flow: Flow<PagingData<RecipeDTO>>){
        val adapter = RecipePagerAdapter(listOf(LongRecipeFingerprint))
        binding.rvRecipes.adapter = adapter
        adapter.retry()
        lifecycleScope.launchWhenStarted {
            Log.d("Debug141", "updateFlow")
            flow.collect {
                adapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        with(binding.rvRecipes){
            layoutManager = GridLayoutManager(requireContext(),2)
            PaddingDecoratorFactory(resources).apply(this,8f,4f)
        }
    }

}