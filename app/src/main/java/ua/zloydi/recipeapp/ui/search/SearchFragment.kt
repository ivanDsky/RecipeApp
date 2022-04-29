package ua.zloydi.recipeapp.ui.search

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.local.SettingsProvider
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentSearchBinding
import ua.zloydi.recipeapp.models.filterTypes.Filter
import ua.zloydi.recipeapp.models.filterTypes.SearchFilter
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.CuisineFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.DishFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.MealFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RetryAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterLayoutManagers.RetrySpanSizeLookup
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import ua.zloydi.recipeapp.ui.search.filter.FilterBottomSheetDialog
import ua.zloydi.recipeapp.utils.setState
import kotlin.properties.Delegates

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

        bindStable()
    }

    private var adapter: LabelAdapter by Delegates.notNull()

    private fun bindState(state: SearchFilter) = with(binding.layoutSearch){
        etQuery.setText(state.search)
        val cuisines = state.filter.cuisines.map { CuisineUI(it.label){} }
        val meals = state.filter.meals.map { MealUI(it.label){} }
        val categories = state.filter.categories.map { DishUI(it.label){} }
        val items = categories+meals+cuisines
        adapter.setItems(items)
        btnReset.isVisible = items.isNotEmpty()
    }

    private fun filter(){
        val dialog = FilterBottomSheetDialog.create(viewModel.stateFlow.value.filter)
        dialog.show(childFragmentManager, null)
        dialog.setFragmentResultListener(FilterBottomSheetDialog.FILTERS){ _, result ->
            viewModel.filter(
                result[FilterBottomSheetDialog.FILTERS] as? Filter
                    ?: return@setFragmentResultListener,
                getQueryText()
            )
        }
    }

    private fun search() {
        viewModel.query(getQueryText())
        closeKeyboard()
    }

    private fun getQueryText() = binding.layoutSearch.etQuery.text?.toString() ?: ""

    private val fingerprint = RecipeFingerprint()
    private var searchAdapter: ConcatAdapter by Delegates.notNull()


    private fun updateFlow(flow: Flow<PagingData<RecipeItemUI>>){
        val adapter = RecipePagerAdapter(listOf(fingerprint))
        adapter.addLoadStateListener {
            binding.layoutRecyclerView.setState(it, adapter)
        }
        searchAdapter = adapter.withLoadStateFooter(RetryAdapter(adapter))
        binding.layoutRecyclerView.rvItems.adapter = searchAdapter
        adapter.retry()
        lifecycleScope.launchWhenStarted {
            Log.d("Debug141", "updateFlow")
            flow.collect {
                adapter.submitData(it)
            }
        }
    }

    private fun bindStable() {
        with(binding.layoutRecyclerView) {
            rvItems.layoutManager = GridLayoutManager(requireContext(), 2).also {
                it.spanSizeLookup = RetrySpanSizeLookup(2) {pos ->
                    pos == searchAdapter.itemCount - 1 && searchAdapter.adapters.last().itemCount > 0
                }
            }
            PaddingDecoratorFactory(resources).apply(rvItems, 8f, 4f)
        }

        with(binding.layoutSearch) {
            btnFilter.setOnClickListener {
                it.isClickable = false
                filter()
                it.postDelayed({it.isClickable = true}, 150)
            }
            btnSearch.setOnClickListener { search() }
            btnReset.setOnClickListener { viewModel.filter(Filter(), getQueryText()) }

            etQuery.setOnKeyListener { _, keyCode, _ ->
                if (keyCode != KeyEvent.KEYCODE_ENTER) return@setOnKeyListener false
                search()
                true
            }

            etQuery.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) closeKeyboard() }

            adapter = LabelAdapter(listOf(DishFingerprint, MealFingerprint, CuisineFingerprint))
            rvLabels.layoutManager =
                FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
            PaddingDecoratorFactory(resources).apply(rvLabels, 4f, 2f)
            rvLabels.adapter = adapter
        }
    }

    private val manager by lazy { requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager }
    private fun closeKeyboard(){
        manager.hideSoftInputFromWindow(binding.layoutSearch.etQuery.windowToken, 0)
        binding.layoutSearch.etQuery.clearFocus()
    }

}