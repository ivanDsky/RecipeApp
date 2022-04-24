package ua.zloydi.recipeapp.ui.categories.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.databinding.FragmentCategorySearchBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import kotlin.properties.Delegates

class CategorySearchFragment private constructor(): BaseFragment<FragmentCategorySearchBinding>() {
    companion object{
        const val CATEGORY = "CATEGORY"

        fun create(searchCategory: RecipeQuery.Category) = CategorySearchFragment().also {
            it.arguments = Bundle().also { it.putSerializable(CATEGORY, searchCategory) }
        }
    }

    override fun inflate(inflater: LayoutInflater) = FragmentCategorySearchBinding.inflate(inflater)
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
        adapter = RecipePagerAdapter(listOf(LongRecipeFingerprint()))
        rvItems.layoutManager = GridLayoutManager(requireContext(), 2)
        rvItems.adapter = adapter
    }
}