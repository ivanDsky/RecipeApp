package ua.zloydi.recipeapp.ui

import android.view.LayoutInflater
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.FragmentTestBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment

class TestFragment : BaseFragment<FragmentTestBinding>(R.layout.fragment_test){
    override fun inflate(inflater: LayoutInflater) = FragmentTestBinding.inflate(inflater)
}