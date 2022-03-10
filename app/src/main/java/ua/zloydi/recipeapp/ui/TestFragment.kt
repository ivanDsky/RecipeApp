package ua.zloydi.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.filter_types.CuisineType
import ua.zloydi.recipeapp.data.filter_types.cuisineMapper
import ua.zloydi.recipeapp.data.filter_types.dishMapper
import ua.zloydi.recipeapp.data.filter_types.mealMapper
import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.databinding.FragmentTestBinding
import ua.zloydi.recipeapp.domain.error.ErrorProvider
import ua.zloydi.recipeapp.domain.repository.RecipeRepository
import ua.zloydi.recipeapp.domain.retrofit.RecipeQuery
import ua.zloydi.recipeapp.domain.retrofit.RetrofitProvider
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint

class TestFragment : BaseFragment<FragmentTestBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentTestBinding.inflate(inflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val repository = RecipeRepository(RetrofitProvider.service, ErrorProvider.service)
            repository.query(RecipeQuery("rice", cuisineType = CuisineType.CentralEurope))?.let {query ->
                val recipes = query.hits?.map { it.recipe }?.map { recipeDTO ->
                    val ingredients =
                        recipeDTO.ingredients?.map { IngredientUI(it.food, it.text, it.measure) }
                            ?: emptyList()
                    val cuisineType = cuisineMapper.enum(recipeDTO.cuisineType?.first())
                    val dishType = dishMapper.enum(recipeDTO.dishType?.first())
                    val mealType = mealMapper.enum(recipeDTO.mealType?.first()?.split("/")?.first())
                    RecipeUI(recipeDTO.label, recipeDTO.image, ingredients, cuisineType, dishType, mealType)
                } ?: emptyList()
                launch(Dispatchers.Main) { setupAdapter(recipes) }
            }
        }
    }

    private fun setupAdapter(list: List<RecipeUI>) {
        with(binding.rvRecipes) {
            layoutManager = LinearLayoutManager(requireContext())
            val adapter = RecipeAdapter(listOf(LongRecipeFingerprint()))
            this.adapter = adapter
            addItemDecoration(PaddingDecoratorFactory(resources).create(8f, 20f, 8f, 0f))
            adapter.setItems(list)
        }
    }

}