package ua.zloydi.recipeapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.AddItem
import ua.zloydi.recipeapp.data.MenuItem
import ua.zloydi.recipeapp.data.TitleItem
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.utils.Navigator
import ua.zloydi.recipeapp.utils.StringItem

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
                viewModel.currentScreenFlow.collect{
                    bindMenu(it)
                    bindTitle(it)
                }
            }
        }
    }

    private fun bindMenu(item: AddItem<*>){
        if (item is MenuItem)
            binding.bottomNavigation.selectedItemId = item.id
        binding.bottomNavigation.isVisible = item is MenuItem
    }

    private fun bindTitle(item: AddItem<*>){
        if (item is TitleItem) {
            binding.toolbar.tvTitle.text =
                when(val stringItem = item.title){
                    is StringItem.Res -> getString(stringItem.id)
                    is StringItem.String -> stringItem.text
                }
        }
        binding.toolbar.tvTitle.isVisible = item is TitleItem
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }
}