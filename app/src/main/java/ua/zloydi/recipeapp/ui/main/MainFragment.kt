package ua.zloydi.recipeapp.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.*
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.receivers.NoInternetReceiver
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.toolbar.IRightButton
import ua.zloydi.recipeapp.utils.Navigator
import ua.zloydi.recipeapp.utils.StringItem


class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)

    private val viewModel: MainFragmentViewModel by activityViewModels()
    private val navigator: Navigator by lazy { Navigator(childFragmentManager, R.id.mainContainer) }
    private val receiver = NoInternetReceiver()
    val childNavigation: IChildNavigation by lazy { viewModel }
    val parentNavigation: IParentNavigation by lazy { viewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
        setupNavigation()
        setupInternetChange()
        setupBackpressed()
        setupToolbar()
    }

    override fun onStart() {
        super.onStart()
        setupDeeplink()
        Log.d("Debug141", "onStart: deeplink")
    }

    private fun setupToolbar() {
        childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            if (fragment is IRightButton) {
                binding.toolbar.btnRight.setImageResource(fragment.rightButton.icon)
                binding.toolbar.btnRight.setOnClickListener(fragment.rightButton.clickListener)
            }
        }
    }

    private fun setupDeeplink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(requireActivity().intent)
            .addOnSuccessListener {
                requireActivity().intent = null
                if (it == null) return@addOnSuccessListener
                val uri = it.link
                Log.d("Debug141", "setupDeeplink: ${uri}")
                val id = uri?.getQueryParameter("id") ?: return@addOnSuccessListener
                viewModel.openDetail(id)
            }
    }

    private var doublePressed = false

    private fun setupBackpressed() {
        val backPressWait = 3000L
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (doublePressed) {
                isEnabled = false
                requireActivity().onBackPressed()
                isEnabled = true
            } else {
                doublePressed = true
                Toast.makeText(requireContext(), R.string.double_press, Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    delay(backPressWait)
                    doublePressed = false
                }
            }
        }
    }

    override fun onDestroyView() {
        requireActivity().unregisterReceiver(receiver)
        super.onDestroyView()
    }

    private fun setupInternetChange() {
        requireActivity().registerReceiver(receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        lifecycleScope.launchWhenStarted {
            receiver.isInternetConnected.collect(::setInternetConnection)
        }
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
                viewModel.currentScreenFlow.collect {
                    bindMenu(it)
                    bindTitle(it)
                    bindToolbar(it)
                }
            }
        }
    }

    private fun bindToolbar(item: AddItem<*>) {
        binding.toolbar.btnBack.isInvisible = item !is ChildItem<*>
        binding.toolbar.btnRight.isInvisible = item !is RightToolbarItem<*>
    }

    private fun bindMenu(item: AddItem<*>) {
        if (item is MenuItem)
            binding.bottomNavigation.selectedItemId = item.id
        binding.bottomNavigation.isVisible = item is MenuItem
    }

    private fun bindTitle(item: AddItem<*>) {
        if (item is TitleItem) {
            binding.toolbar.tvTitle.text =
                when (val stringItem = item.title) {
                    is StringItem.Res -> getString(stringItem.id)
                    is StringItem.String -> stringItem.text
                }
        }
        binding.toolbar.tvTitle.isVisible = item is TitleItem
    }

    private fun setInternetConnection(isInternetConnected: Boolean) {
        binding.toolbar.tvNoInternetConnection.isVisible = !isInternetConnected
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }
}