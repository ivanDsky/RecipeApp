package ua.zloydi.recipeapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.FragmentTestBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import kotlin.random.Random

class TestFragment : BaseFragment<FragmentTestBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentTestBinding.inflate(inflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = Random(System.currentTimeMillis())
            .nextInt(10, 100)
            .toString()
        Log.d("Debug141", "onViewCreated: ${binding.tvTitle.text}")
    }
}