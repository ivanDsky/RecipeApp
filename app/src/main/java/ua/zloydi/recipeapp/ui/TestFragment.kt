package ua.zloydi.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.dto.RecipeDTO
import ua.zloydi.recipeapp.data.filter_types.*
import ua.zloydi.recipeapp.data.ui.IngredientUI
import ua.zloydi.recipeapp.data.ui.RecipeItemUI
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
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseAdapter
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe.LongRecipeFingerprint
import ua.zloydi.recipeapp.ui.detail.DetailFragment

class TestFragment : BaseFragment<FragmentTestBinding>() {
    private var recipes: List<RecipeDTO>? = null
    override fun inflate(inflater: LayoutInflater) = FragmentTestBinding.inflate(inflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val repository = RecipeRepository(RetrofitProvider.service, ErrorProvider.service)
            repository.query(RecipeQuery("", cuisineType = Cuisine.CentralEurope))?.let { query ->
                recipes = query.hits?.map { it.recipe }
                val uiRecipes = recipes?.map { recipeDTO ->
                    val types = mutableListOf<FilterType?>()
                    recipeDTO.mealType?.forEach { it.split("/").forEach { types.add(MealMapper.enum(it)) } }
                    recipeDTO.dishType?.forEach { it.split("/").forEach { types.add(DishMapper.enum(it)) } }
                    recipeDTO.cuisineType?.forEach { it.split("/").forEach { types.add(CuisineMapper.enum(it)) } }
                    RecipeItemUI(recipeDTO.label, recipeDTO.image, recipeDTO.totalTime, types.mapNotNull {
                        when(it){
                            is Meal -> MealUI(it.label)
                            is Dish -> DishUI(it.label)
                            is Cuisine -> CuisineUI(it.label)
                            else -> null
                        }
                    })
                } ?: emptyList()
                launch(Dispatchers.Main) { setupAdapter(uiRecipes) }
            }
        }
    }


    private fun setupAdapter(list: List<RecipeItemUI>) {
        with(binding.rvRecipes) {
            layoutManager = GridLayoutManager(requireContext(),2)
            lateinit var adapter: RecipeAdapter
            val clickListener = BaseAdapter.OnItemClickListener { binding, position ->
                val item = adapter.getItem(position)
                requireActivity().supportFragmentManager.commit {
                    val dto = recipes?.find { it.image == item.image && it.label == item.title} ?: return@commit
                    val ingredients = dto.ingredients?.map { IngredientUI(it.food, it.text, it.measure) }
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
                    add(R.id.mainContainer,DetailFragment.create(recipeUI))
                    addToBackStack(null)
                }
            }
            adapter = RecipeAdapter(listOf(LongRecipeFingerprint),clickListener)
            this.adapter = adapter
            PaddingDecoratorFactory(resources).apply(this, 8f, 8f)
            adapter.setItems(list)
        }
    }

}