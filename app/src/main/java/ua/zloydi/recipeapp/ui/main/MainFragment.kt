package ua.zloydi.recipeapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.NavigationItem
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)

    private val viewModel: MainFragmentViewModel by activityViewModels()
    val childNavigation: IChildNavigation by lazy { viewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupBottomNavigation()
    }

    private fun setupNavigation() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationScreenFlow.collect{it.bind()}
        }
    }

    private fun NavigationItem.bind(){
        binding.toolbar.tvTitle.text = getString(title)
        binding.bottomNavigation.selectedItemId = id
        replaceFragment(fragmentFactory, tag)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragmentFactory: () -> Fragment, tag: String) {
        childFragmentManager.commit {
            val fragment = childFragmentManager.findFragmentByTag(tag)
            childFragmentManager.fragments.forEach { hide(it) }
            if(fragment == null) {
                if(tag == "Detail") addToBackStack(null)
                add(R.id.mainContainer, fragmentFactory(), tag)
            }else
                show(fragment)
        }
    }

}