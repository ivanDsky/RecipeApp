package ua.zloydi.recipeapp.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarksProvider
import ua.zloydi.recipeapp.data.local.cache.CacheProvider
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
import ua.zloydi.recipeapp.ui.core.toolbar.DrawableButtonContent
import ua.zloydi.recipeapp.ui.core.toolbar.IRightButton
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import ua.zloydi.recipeapp.utils.CookingTime
import kotlin.properties.Delegates

class DetailFragment : BaseFragment<FragmentDetailBinding>(), IRightButton{
    companion object{
        private const val RECIPE = "RECIPE"
        private const val ID = "ID"
        fun create(id: String): DetailFragment{
            return DetailFragment().apply { arguments = bundleOf(ID to id) }
        }
        fun create(recipe: RecipeItemDTO): DetailFragment{
            return DetailFragment().apply { arguments = bundleOf(ID to recipe.id, RECIPE to recipe) }
        }
    }
    override fun inflate(inflater: LayoutInflater) = FragmentDetailBinding.inflate(inflater)
    private val viewModel: DetailFragmentViewModel by viewModels{
        DetailFragmentViewModel.Factory(
            requireArguments()[ID] as String,
            requireArguments()[RECIPE] as? RecipeItemDTO,
            CacheProvider.database,
            BookmarksProvider.database,
            RecipeProvider.repository,
            (parentFragment as MainFragment).parentNavigation
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Debug141", "onViewCreated: ${viewModel.recipe?.id}")
        bindStable(viewModel.recipe?.label)
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
                .addListener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean,
                    ): Boolean {
                        shimmerRecipePreview.hideShimmer()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any?, target: Target<Drawable>?,
                        dataSource: DataSource?, isFirstResource: Boolean,
                    ): Boolean {
                        shimmerRecipePreview.hideShimmer()
                        return false
                    }
                })
                .placeholder(R.drawable.logo)
                .into(ivRecipePreview)

            tvTitle.text = title
            shimmerTitle.hideShimmer()

            CookingTime.setTime(tvTime, totalTime)

            tvSource.text = Html.fromHtml(getString(R.string.link, url))
            tvSource.movementMethod = LinkMovementMethod()
            tvSource.isVisible = true

            labelAdapter.setItems(dishType + mealType + cuisineType)
            rvLabels.isVisible = true
            ingredientAdapter.setItems(ingredients)
            rvIngredients.isVisible = true

            btnBookmark.setOnClickListener { viewModel.changeBookmark() }
            btnBookmark.isVisible = true
        }}

    private fun bindBookmark(isBookmarked: Boolean){
        binding.btnBookmark.setImageResource(
            if (isBookmarked) R.drawable.bookmarked else R.drawable.bookmark
        )
    }

    override val rightButton = DrawableButtonContent(R.drawable.share){
        shareItem()
    }

    private fun shareItem() = lifecycleScope.launchWhenStarted{
        requireContext().startActivity(viewModel.shareIntent())
    }
}