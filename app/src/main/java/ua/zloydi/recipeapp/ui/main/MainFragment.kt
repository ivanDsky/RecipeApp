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
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)

    private val viewModel: MainFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupBottomNavigation()
    }

    private fun setupNavigation() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigationScreenFlow.collect {
                binding.toolbar.tvTitle.text = getString(it.title)
                binding.bottomNavigation.selectedItemId = it.id
                replaceFragment(it.fragment)
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.mainContainer, fragment)
        }
    }

}