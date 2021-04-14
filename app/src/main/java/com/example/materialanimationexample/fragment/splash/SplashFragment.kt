package com.example.materialanimationexample.fragment.splash

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.materialanimationexample.MainViewModel
import com.example.materialanimationexample.R
import com.example.materialanimationexample.databinding.SplashLayoutFragmentBinding
import com.example.materialanimationexample.fragment.KEY_FIRST_OPEN_APP
import com.example.materialanimationexample.fragment.PREFERENCE_KEY


class SplashFragment : Fragment() {
    private var _binding: SplashLayoutFragmentBinding? = null
    private var viewModel: SplashFragmentViewModel? = null
    private var mainViewModel :MainViewModel? = null
    private val binding get() = _binding!!

    private val obNavigateHome = Observer<Boolean> {
        if (it)
            navigateHome()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashLayoutFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SplashFragmentViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        mainViewModel?.setEnableDrawerLayout(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.isNavigationHome()?.observe(viewLifecycleOwner, obNavigateHome)
        binding.containedButton.setOnClickListener {
            context?.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)?.edit()?.putBoolean(
                KEY_FIRST_OPEN_APP, true
            )?.apply()
            navigateHome()
        }
    }

    private fun navigateHome() {
        val findNavController = Navigation.findNavController(requireView())
        findNavController.navigate(R.id.action_splashFragment_to_home_navigation)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}