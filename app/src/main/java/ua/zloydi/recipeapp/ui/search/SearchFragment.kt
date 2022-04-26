package ua.zloydi.recipeapp.ui.search

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.local.SettingsProvider
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentSearchBinding
import ua.zloydi.recipeapp.models.filter_types.Filter
import ua.zloydi.recipeapp.models.filter_types.SearchFilter
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import ua.zloydi.recipeapp.ui.search.filter.FilterBottomSheetDialog

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    companion object{
        private const val QUERY = "QUERY"
        fun create(searchFilter: SearchFilter?) = SearchFragment().apply {
            arguments = bundleOf(QUERY to searchFilter)
        }
    }

    fun setQuery(searchFilter: SearchFilter?) = searchFilter?.let { viewModel.sendQuery(it) }

    private val viewModel: SearchFragmentViewModel by viewModels {
        SearchFragmentViewModel.Factory(
            arguments?.get(QUERY) as? SearchFilter,
            SettingsProvider.repository,
            RecipeProvider.repository,
            (parentFragment as MainFragment).childNavigation
        )
    }
    override fun inflate(inflater: LayoutInflater) = FragmentSearchBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            launch { viewModel.stateFlow.collectLatest(::bindState) }
            launch { viewModel.searchFlow.filterNotNull().collect(::updateFlow) }
        }

        with(binding.layoutSearch){
            btnFilter.setOnClickListener { filter() }
            btnSearch.setOnClickListener { search(etQuery) }
        }

        bindStable()
    }

    private fun bindState(state: SearchFilter) = with(binding.layoutSearch){
        etQuery.setText(state.search)
    }

    private fun filter(){
        val listener = FragmentResultListener{ _, result ->
            viewModel.filter(
                result[FilterBottomSheetDialog.FILTERS] as? Filter ?: return@FragmentResultListener,
                binding.layoutSearch.etQuery.text?.toString() ?: ""
            )
        }
        childFragmentManager.setFragmentResultListener(
            FilterBottomSheetDialog.FILTERS,
            viewLifecycleOwner,
            listener
        )
        FilterBottomSheetDialog.create(viewModel.stateFlow.value.filter)
            .show(childFragmentManager, null)
    }

    private fun search(editText: EditText) {
        viewModel.query(editText.text?.toString())
        closeKeyboard()
    }

    private val fingerprint = LongRecipeFingerprint()

    private fun updateFlow(flow: Flow<PagingData<RecipeItemUI>>){
        val adapter = RecipePagerAdapter(listOf(fingerprint))
        binding.rvRecipes.adapter = adapter
        adapter.retry()
        lifecycleScope.launchWhenStarted {
            Log.d("Debug141", "updateFlow")
            flow.collect {
                adapter.submitData(it)
            }
        }
    }

    private fun bindStable() = with(binding){
        rvRecipes.layoutManager = GridLayoutManager(requireContext(),2)
        PaddingDecoratorFactory(resources).apply(rvRecipes,8f,4f)

        layoutSearch.etQuery.setOnKeyListener { _, keyCode, _ ->
            if (keyCode != KeyEvent.KEYCODE_ENTER) return@setOnKeyListener false
            search(layoutSearch.etQuery)
            true
        }
    }

    private fun closeKeyboard(){
        val inputManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.layoutSearch.etQuery.windowToken, 0)
        binding.layoutSearch.etQuery.clearFocus()
    }

}