package ua.zloydi.recipeapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.FragmentSearchBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.RecipeAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.LongRecipeFingerprint

class SearchFragment : BaseFragment<FragmentSearchBinding>(){
    private val viewModel: SearchFragmentViewModel by viewModels()
    override fun inflate(inflater: LayoutInflater) = FragmentSearchBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        with(binding.rvRecipes){
            layoutManager = LinearLayoutManager(requireContext())
            val adapter = RecipeAdapter(listOf(LongRecipeFingerprint()))
            this.adapter = adapter
            addItemDecoration(PaddingDecoratorFactory(resources).create(8f,20f,8f,0f))
            adapter.setItems(viewModel.getItems(resources))
        }
    }

}