package ua.zloydi.recipeapp.ui.categories.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.databinding.FragmentRecyclerViewBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RetryAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterLayoutManagers.RetrySpanSizeLookup
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import ua.zloydi.recipeapp.utils.setLoading
import kotlin.properties.Delegates

class CategorySearchFragment : BaseFragment<FragmentRecyclerViewBinding>() {
    companion object{
        const val CATEGORY = "CATEGORY"

        fun create(searchCategory: RecipeQuery.Category) = CategorySearchFragment().also {
            it.arguments = Bundle().also { it.putSerializable(CATEGORY, searchCategory) }
        }
    }

    override fun inflate(inflater: LayoutInflater) = FragmentRecyclerViewBinding.inflate(inflater)
    private val viewModel: CategorySearchViewModel by viewModels {
        val parentFragment = (parentFragment as MainFragment)
        CategorySearchViewModel.Factory(RecipeProvider.repository,
            requireArguments()[CATEGORY] as RecipeQuery.Category,
            parentFragment.parentNavigation,
            parentFragment.childNavigation)
    }

    private var adapter: RecipePagerAdapter by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindStable()
        lifecycleScope.launchWhenStarted {
            viewModel.uiFlow.collect(::bind)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.openParent()
        }
    }

    private suspend fun bind(data: PagingData<RecipeItemUI>) {
        adapter.submitData(data)
    }

    private fun bindStable() = with(binding){
        adapter = RecipePagerAdapter(listOf(RecipeFingerprint()))
        adapter.addLoadStateListener {
            binding.setLoading(it.refresh == LoadState.Loading)
        }
        val concatAdapter = adapter.withLoadStateFooter(RetryAdapter(adapter))
        PaddingDecoratorFactory(resources).apply(rvItems,8f,4f)
        rvItems.layoutManager = GridLayoutManager(requireContext(), 2).also {
            it.spanSizeLookup = RetrySpanSizeLookup(2){concatAdapter.itemCount}
        }
        rvItems.adapter = concatAdapter
    }
}