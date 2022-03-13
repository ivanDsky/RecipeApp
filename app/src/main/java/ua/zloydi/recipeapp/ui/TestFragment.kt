package ua.zloydi.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.data.filter_types.*
import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.data.ui.RecipeUI
import ua.zloydi.recipeapp.data.ui.filterType.CuisineUI
import ua.zloydi.recipeapp.data.ui.filterType.DishUI
import ua.zloydi.recipeapp.data.ui.filterType.MealUI
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
            repository.query(RecipeQuery("", cuisineType = Cuisine.CentralEurope))?.let { query ->
                val recipes = query.hits?.map { it.recipe }?.map { recipeDTO ->
                    val ingredients =
                        recipeDTO.ingredients?.map { IngredientUI(it.food, it.text, it.measure) }
                            ?: emptyList()
                    val types = mutableListOf<FilterType?>()
                    recipeDTO.mealType?.forEach { it.split("/").forEach { types.add(MealMapper.enum(it)) } }
                    recipeDTO.dishType?.forEach { it.split("/").forEach { types.add(DishMapper.enum(it)) } }
                    recipeDTO.cuisineType?.forEach { it.split("/").forEach { types.add(CuisineMapper.enum(it)) } }
                    RecipeUI(recipeDTO.label, recipeDTO.image, recipeDTO.totalTime, ingredients, types.mapNotNull {
                        when(it){
                            is Meal -> MealUI(it.label)
                            is Dish -> DishUI(it.label)
                            is Cuisine -> CuisineUI(it.label)
                            else -> null
                        }
                    })
//                    RecipeUI(recipeDTO.label, recipeDTO.image, ingredients, emptyList())
                } ?: emptyList()
                launch(Dispatchers.Main) { setupAdapter(recipes) }
            }
        }
    }


    private fun setupAdapter(list: List<RecipeUI>) {
        with(binding.rvRecipes) {
            val span = 2
            layoutManager = GridLayoutManager(requireContext(),span)
            val adapter = RecipeAdapter(listOf(LongRecipeFingerprint()))
            this.adapter = adapter
            PaddingDecoratorFactory(resources).apply(this, 8f, 8f)
            adapter.setItems(list)
        }
    }

}