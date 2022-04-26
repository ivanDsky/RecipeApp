package ua.zloydi.recipeapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.AddItem
import ua.zloydi.recipeapp.data.MenuItem
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
            launch {
                viewModel.navigationActions.collect {
                    navigator.bindNavigation(it)
                    if (it is AddItem<*>) viewModel.navigationSelect(it)
                }
            }
            launch {
                viewModel.currentScreenFlow.filterIsInstance<MenuItem<*>>().collect(::bindMenu)
            }
        }
    }

    private fun bindMenu(item: MenuItem<*>){
        binding.toolbar.tvTitle.text = getString(item.title)
        binding.bottomNavigation.selectedItemId = item.id
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }
}