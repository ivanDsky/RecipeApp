package ua.zloydi.recipeapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.MenuItem
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.utils.Navigator

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)

    private val viewModel: MainFragmentViewModel by activityViewModels()
    private val navigator: Navigator by lazy { Navigator(childFragmentManager, R.id.mainContainer) }
    val childNavigation: IChildNavigation by lazy { viewModel }
    val parentNavigation: IParentNavigation by lazy { viewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
        setupNavigation()
    }

    private fun setupNavigation() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationScreenFlow.collect(::bindNavigation)
        }
    }

    private fun bindNavigation(item: NavigationItem) {
        when(item){
            is MenuItem -> bindMenu(item)
        }
        navigator.bindNavigation(item)
    }

    private fun bindMenu(item: MenuItem){
        binding.toolbar.tvTitle.text = getString(item.title)
        binding.bottomNavigation.id = item.id
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }
}