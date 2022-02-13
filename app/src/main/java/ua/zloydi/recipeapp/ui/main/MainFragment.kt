package ua.zloydi.recipeapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main){
    override fun inflate(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}