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
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.ErrorProvider
import ua.zloydi.recipeapp.data.repository.RecipeRepository
import ua.zloydi.recipeapp.data.retrofit.RetrofitProvider
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
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI
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
            RecipeRepository(RetrofitProvider.service,
                ErrorProvider.service),
            (parentFragment as MainFragment).parentNavigation
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val recipe = viewModel.getRecipeUI(getRecipe())
            if (recipe == null)
                requireActivity().onBackPressed()
            else {
                viewModel.recipe = recipe
                bind()
            }
        }
//        sharedElementEnterTransition = MaterialContainerTransform().apply {
//            containerColor = Color.BLACK
//            drawingViewId = R.id.mainContainer
//            duration = 2000
//        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            viewModel.openParent()
        }
    }

    private fun bind() =
        with(binding){
        with(viewModel.recipe) {
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

            createLabels(rvLabels,cuisineType, dishType, mealType)
            createIngredients(rvIngredients, ingredients)

            description?.let {
                tvDescription.text = it
                tvDescription.isVisible = true
            } ?: run {
                tvDescription.isVisible = false
            }
        }}

    private fun createIngredients(rvIngredients: RecyclerView, ingredients: Array<IngredientUI>?) {
        rvIngredients.layoutManager = LinearLayoutManager(requireContext())
        val adapter = IngredientAdapter()
        rvIngredients.adapter = adapter
        PaddingDecoratorFactory(resources).apply(rvIngredients,2f, 0f, false)
        adapter.setItems(ingredients?.toList() ?: emptyList())
    }

    private fun createLabels(
        rvLabels: RecyclerView,
        cuisineType: Array<CuisineUI>?,
        dishType: Array<DishUI>?,
        mealType: Array<MealUI>?
    ) {
        rvLabels.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var items = emptyArray<FilterTypeUI>()
        if(cuisineType != null) items += cuisineType
        if(dishType != null) items += dishType
        if(mealType != null) items += mealType

        val adapter = LabelAdapter(listOf(CuisineFingerprint, DishFingerprint, MealFingerprint))
        rvLabels.adapter = adapter
        adapter.setItems(items.asList())

        PaddingDecoratorFactory(resources).apply(rvLabels, 0f, 2f, false)
    }

    private fun getRecipe(): RecipeItemDTO {
        val obj = (arguments?.get(RECIPE) ?: throw IllegalStateException("Incorrect initialization"))
        if(obj !is RecipeItemDTO) throw TypeCastException("Incorrect parameter in RECIPE field")
        return obj
    }
}