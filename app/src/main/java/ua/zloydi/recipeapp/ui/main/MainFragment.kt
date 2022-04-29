package ua.zloydi.recipeapp.ui.main

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.AddItem
import ua.zloydi.recipeapp.data.ChildItem
import ua.zloydi.recipeapp.data.MenuItem
import ua.zloydi.recipeapp.data.TitleItem
import ua.zloydi.recipeapp.databinding.FragmentMainBinding
import ua.zloydi.recipeapp.receivers.NoInternetReceiver
import ua.zloydi.recipeapp.ui.core.BaseFragment
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
    }

    private var doublePressed = false

    private fun setupBackpressed() {
        val backPressWait = 3000L
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            if (doublePressed) {
                isEnabled = false
                requireActivity().onBackPressed()
                isEnabled = true
            } else {
                doublePressed = true
                Toast.makeText(requireContext(),R.string.double_press,Toast.LENGTH_SHORT).show()
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
        requireActivity().registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
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
                viewModel.currentScreenFlow.collect{
                    bindMenu(it)
                    bindTitle(it)
                    bindBackButton(it)
                }
            }
        }
    }

    private fun bindBackButton(item: AddItem<*>) {
        binding.toolbar.btnBack.isInvisible = item !is ChildItem<*>
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

    private fun setInternetConnection(isInternetConnected: Boolean){
        binding.toolbar.tvNoInternetConnection.isVisible = !isInternetConnected
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            viewModel.onMenuSelected(it)
            return@setOnItemSelectedListener true
        }
    }
}