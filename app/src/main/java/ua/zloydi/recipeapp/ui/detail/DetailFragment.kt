package ua.zloydi.recipeapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarksProvider
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentDetailBinding
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter.IngredientAdapter
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.CuisineFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.DishFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.MealFingerprint
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI
import ua.zloydi.recipeapp.ui.main.MainFragment

class DetailFragment private constructor(): BaseFragment<FragmentDetailBinding>(){
    companion object{
        private const val RECIPE = "RECIPE"
        fun create(recipe: RecipeItemDTO): DetailFragment{
            return DetailFragment().apply { arguments = bundleOf(RECIPE to recipe) }
        }
    }
    override fun inflate(inflater: LayoutInflater) = FragmentDetailBinding.inflate(inflater)
    private val viewModel: DetailFragmentViewModel by viewModels{
        DetailFragmentViewModel.Factory(
            requireArguments()[RECIPE] as RecipeItemDTO,
            BookmarksProvider.database,
            RecipeProvider.repository,
            (parentFragment as MainFragment).parentNavigation
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val recipe = viewModel.recipeUI.await()
            if (recipe == null)
                requireActivity().onBackPressed()
            else {
                bind(recipe)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.isBookmarked.collect(::bindBookmark)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            viewModel.openParent()
        }
    }

    private fun bind(recipe: RecipeUI) =
        with(binding){
        with(recipe) {
            Glide.with(ivRecipePreview)
                .load(image)
                .into(ivRecipePreview)

            tvTitle.text = title

            if(totalTime != null && totalTime in 1f..240f){
                tvTime.text = getString(R.string.time, totalTime)
                tvTime.isVisible = true
            }else{
                tvTime.isVisible = false
            }
            tvSource.text = url

            createLabels(rvLabels, cuisineType, dishType, mealType)
            createIngredients(rvIngredients, ingredients)

            description?.let {
                tvDescription.text = it
                tvDescription.isVisible = true
            } ?: run {
                tvDescription.isVisible = false
            }

            btnBookmark.setOnClickListener { viewModel.changeBookmark() }
        }}

    private fun bindBookmark(isBookmarked: Boolean){
        binding.btnBookmark.setImageResource(
            if (isBookmarked) R.drawable.bookmarked else R.drawable.bookmark
        )
    }

    private fun createIngredients(rvIngredients: RecyclerView, ingredients: List<IngredientUI>) {
        rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        val adapter = IngredientAdapter()
        rvIngredients.adapter = adapter
        PaddingDecoratorFactory(resources).apply(rvIngredients,2f, 0f, false)
        adapter.setItems(ingredients)
    }

    private fun createLabels(
        rvLabels: RecyclerView,
        cuisineType: List<CuisineUI>,
        dishType: List<DishUI>,
        mealType: List<MealUI>
    ) {
        rvLabels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val items = dishType + mealType + cuisineType

        val adapter = LabelAdapter(listOf(DishFingerprint, MealFingerprint, CuisineFingerprint))
        rvLabels.adapter = adapter
        adapter.setItems(items)

        PaddingDecoratorFactory(resources).apply(rvLabels, 0f, 2f, false)
    }
}