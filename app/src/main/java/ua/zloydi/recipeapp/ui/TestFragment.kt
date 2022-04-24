package ua.zloydi.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentTestBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint
import ua.zloydi.recipeapp.ui.main.MainFragment

class TestFragment : BaseFragment<FragmentTestBinding>() {
    private val viewModel: TestFragmentVM by viewModels{
        TestFragmentVM.Factory(RecipeProvider.repository,
            (parentFragment as MainFragment).childNavigation)
    }
    override fun inflate(inflater: LayoutInflater) = FragmentTestBinding.inflate(inflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.mainContainer
            duration
        }
        setupAdapter()
    }

    private lateinit var adapter: RecipePagerAdapter

    private fun setupAdapter() {
        with(binding.rvRecipes) {
            layoutManager = GridLayoutManager(requireContext(),2)
            this@TestFragment.adapter = RecipePagerAdapter(listOf(LongRecipeFingerprint()))
            adapter = this@TestFragment.adapter
            lifecycleScope.launchWhenStarted {
                viewModel.flow.collect {this@TestFragment.adapter.submitData(it)}
            }
            PaddingDecoratorFactory(resources).apply(this, 8f, 8f)
            doOnPreDraw { startPostponedEnterTransition() }
        }
    }

}