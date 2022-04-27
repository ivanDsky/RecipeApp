package ua.zloydi.recipeapp.ui.detail

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarksProvider
import ua.zloydi.recipeapp.data.repository.RecipeProvider
import ua.zloydi.recipeapp.databinding.FragmentDetailBinding
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.ingredientAdapter.IngredientAdapter
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.CuisineFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.DishFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.MealFingerprint
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import kotlin.properties.Delegates

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
        bindStable(viewModel.recipe.label)
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

    private var ingredientAdapter: IngredientAdapter by Delegates.notNull()
    private var labelAdapter: LabelAdapter by Delegates.notNull()

    private fun bindStable(title: String?) = with(binding){
        if (title != null) tvTitle.text = title
        shimmerTitle.startShimmer()
        shimmerRecipePreview.startShimmer()

        rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        ingredientAdapter = IngredientAdapter()
        rvIngredients.adapter = ingredientAdapter
        PaddingDecoratorFactory(resources).apply(rvIngredients,2f, 0f, false)

        rvLabels.layoutManager =
            FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP)
        labelAdapter = LabelAdapter(listOf(DishFingerprint, MealFingerprint, CuisineFingerprint))
        rvLabels.adapter = labelAdapter
        PaddingDecoratorFactory(resources).apply(rvLabels, 4f, 2f, false)
    }

    private fun bind(recipe: RecipeUI) =
        with(binding){
        with(recipe) {
            Glide.with(ivRecipePreview)
                .load(image)
                .into(ivRecipePreview)
            shimmerRecipePreview.hideShimmer()

            tvTitle.text = title
            shimmerTitle.hideShimmer()

            if(totalTime != null && totalTime in 1f..240f){
                tvTime.text = getString(R.string.time, totalTime)
                tvTime.isVisible = true
            }else{
                tvTime.isVisible = false
            }

            tvSource.text = Html.fromHtml(getString(R.string.link, url))
            tvSource.movementMethod = LinkMovementMethod()
            tvSource.isVisible = true

            labelAdapter.setItems(dishType + mealType + cuisineType)
            rvLabels.isVisible = true
            ingredientAdapter.setItems(ingredients)

            btnBookmark.setOnClickListener { viewModel.changeBookmark() }
            btnBookmark.isVisible = true
        }}

    private fun bindBookmark(isBookmarked: Boolean){
        binding.btnBookmark.setImageResource(
            if (isBookmarked) R.drawable.bookmarked else R.drawable.bookmark
        )
    }
}