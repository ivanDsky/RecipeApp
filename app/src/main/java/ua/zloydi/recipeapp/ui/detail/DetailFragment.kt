package ua.zloydi.recipeapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialContainerTransform
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.FragmentDetailBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment

class DetailFragment : BaseFragment<FragmentDetailBinding>(){
    override fun inflate(inflater: LayoutInflater) = FragmentDetailBinding.inflate(inflater)
    private val viewModel: DetailFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply{
            drawingViewId = R.id.mainContainer
            duration = 2000
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(resources.getColor(R.color.orange_light,null))
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            parentFragmentManager.popBackStack()
        }
        binding.root.setOnClickListener { requireActivity().onBackPressed() }
    }
}