package ua.zloydi.recipeapp.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(@LayoutRes res: Int) : Fragment(res){
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw NullPointerException("View Binding is null")

    abstract fun inflate(inflater: LayoutInflater) : VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}