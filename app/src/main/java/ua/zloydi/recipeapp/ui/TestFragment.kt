package ua.zloydi.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.runBlocking
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.filter_types.CuisineMapper
import ua.zloydi.recipeapp.data.filter_types.DishMapper
import ua.zloydi.recipeapp.data.filter_types.MealMapper
import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.data.ui.RecipeItemUI
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.data.ui.filterType.CuisineUI
import ua.zloydi.recipeapp.data.ui.filterType.DishUI
import ua.zloydi.recipeapp.data.ui.filterType.MealUI
import ua.zloydi.recipeapp.databinding.FragmentTestBinding
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint
import ua.zloydi.recipeapp.ui.detail.DetailFragment

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
        lifecycleScope.launchWhenCreated {
            setupAdapter(viewModel.uiRecipes.await())
        }
    }


    private fun setupAdapter(list: List<RecipeItemUI>) {
        with(binding.rvRecipes) {
            layoutManager = GridLayoutManager(requireContext(),2)
            lateinit var adapter: RecipeAdapter
            val clickListener = BaseAdapter.OnItemClickListener { binding, position ->
                val b = binding as LayoutLongRecipeItemBinding
                val item = adapter.getItem(position)
                parentFragmentManager.commit {
                    val dto = runBlocking { viewModel.recipes.await() }?.find { it.image == item.image && it.label == item.title} ?: return@commit
                    val ingredients = dto.ingredients?.map { IngredientUI(it.food, it.text, it.measure, it.image) }
                    val cuisine = mutableListOf<CuisineUI>()
                        dto.cuisineType?.forEach { it.split('/').forEach {CuisineMapper.enum(it)?.label?.let { cuisine.add(CuisineUI(it)) }} }
                    val meal = mutableListOf<MealUI>()
                        dto.mealType?.forEach { it.split('/').forEach {MealMapper.enum(it)?.label?.let { meal.add(MealUI(it)) }} }
                    val dish = mutableListOf<DishUI>()
                        dto.dishType?.forEach { it.split('/').forEach {DishMapper.enum(it)?.label?.let { dish.add(DishUI(it)) }} }
                    val recipeUI = RecipeUI(
                        dto.label,
                        dto.image,
                        dto.source,
                        null,
                        dto.url,
                        ingredients?.toTypedArray(),
                        dto.calories,
                        dto.totalTime,
                        cuisine.toTypedArray(),
                        meal.toTypedArray(),
                        dish.toTypedArray()
                    )
                    setReorderingAllowed(true)
                    addSharedElement(b.ivRecipePreview,"ivRecipePreview")
                    replace(R.id.mainContainer,DetailFragment.create(recipeUI))
                    addToBackStack(null)
                }
            }
            adapter = RecipeAdapter(listOf(LongRecipeFingerprint),clickListener)
            this.adapter = adapter
            PaddingDecoratorFactory(resources).apply(this, 8f, 8f)
            adapter.setItems(list)
            doOnPreDraw { startPostponedEnterTransition() }
        }
    }

}