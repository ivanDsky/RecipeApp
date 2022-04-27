package ua.zloydi.recipeapp.ui.search.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.DialogFilterBinding
import ua.zloydi.recipeapp.models.filterTypes.Cuisine
import ua.zloydi.recipeapp.models.filterTypes.Dish
import ua.zloydi.recipeapp.models.filterTypes.Filter
import ua.zloydi.recipeapp.models.filterTypes.Meal
import ua.zloydi.recipeapp.ui.core.adapter.filterAdapter.FilterAdapter
import ua.zloydi.recipeapp.ui.core.adapter.filterAdapter.FilterFingerprint
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import kotlin.properties.Delegates

class FilterBottomSheetDialog private constructor(): BottomSheetDialogFragment() {
    companion object{
        const val FILTERS = "FILTERS"
        fun create(filter: Filter) = FilterBottomSheetDialog().apply {
            arguments = bundleOf(FILTERS to filter)
        }
    }
    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding ?: throw NullPointerException()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFilterBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private val viewModel: FilterViewModel by viewModels{
        FilterViewModel.Factory(requireArguments()[FILTERS] as Filter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindStable()
        lifecycleScope.launchWhenStarted {
            launch {  viewModel.state.collect(::bindState)}
            launch {  viewModel.actions.collect(::bindAction) }
        }
    }

    private var filterCategoryAdapter: FilterAdapter by Delegates.notNull()
    private var filterMealAdapter: FilterAdapter by Delegates.notNull()
    private var filterCuisineAdapter: FilterAdapter by Delegates.notNull()

    private fun bindStable() = with(binding){
        btnFilter.setOnClickListener { setResult(viewModel.getFilter()) }
        btnReset.setOnClickListener { viewModel.reset() }

        fun RecyclerView.bindAdapter(filterAdapter: FilterAdapter){
            adapter = filterAdapter
            layoutManager = FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
            PaddingDecoratorFactory(resources).apply(this, 4f, 4f)
        }
        filterCategoryAdapter = FilterAdapter(FilterFingerprint.Category)
        filterCategory.tvTitle.text = getString(R.string.category)
        filterCategory.rvItems.bindAdapter(filterCategoryAdapter)
        filterMealAdapter = FilterAdapter(FilterFingerprint.Meal)
        filterMeal.tvTitle.text = getString(R.string.meal)
        filterMeal.rvItems.bindAdapter(filterMealAdapter)
        filterCuisineAdapter = FilterAdapter(FilterFingerprint.Cuisine)
        filterCuisine.tvTitle.text = getString(R.string.cuisine)
        filterCuisine.rvItems.bindAdapter(filterCuisineAdapter)
    }

    private fun bindState(state: FilterState) {
        filterCategoryAdapter.setItems(state.categories)
        filterMealAdapter.setItems(state.meals)
        filterCuisineAdapter.setItems(state.cuisines)
    }

    private fun bindAction(action: Action) = when(action){
        is Action.Select -> {
            val adapter = when(action.type){
                is Cuisine -> filterCuisineAdapter
                is Dish -> filterCategoryAdapter
                is Meal -> filterMealAdapter
            }
            adapter.notifyItemChanged(action.position, Unit)
        }
        Action.UnselectAll -> {
            filterCategoryAdapter.notifyDataSetChanged()
            filterMealAdapter.notifyDataSetChanged()
            filterCuisineAdapter.notifyDataSetChanged()
        }
    }

    private fun setResult(filter: Filter){
        setFragmentResult(FILTERS, Bundle().also { it.putSerializable(FILTERS, filter) })
        dismiss()
    }
}