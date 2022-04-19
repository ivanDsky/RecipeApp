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
import ua.zloydi.recipeapp.databinding.FragmentTestBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipePagerAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.MainFragment

class TestFragment : BaseFragment<FragmentTestBinding>() {
    private val viewModel: TestFragmentVM by viewModels()
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

    private fun setupAdapter() {
        with(binding.rvRecipes) {
            layoutManager = GridLayoutManager(requireContext(),2)
            lateinit var adapter: RecipePagerAdapter
            lifecycleScope.launchWhenStarted {
                viewModel.flow.collect {
                    adapter.submitData(it)
                }
            }
            val clickListener : (RecipeItemUI) -> Unit = {item ->
                (parentFragment as MainFragment).childNavigation.openDetail(item)
            }
            adapter = RecipePagerAdapter(listOf(LongRecipeFingerprint(clickListener)))
            this.adapter = adapter
            PaddingDecoratorFactory(resources).apply(this, 8f, 8f)
            doOnPreDraw { startPostponedEnterTransition() }
        }
    }

}