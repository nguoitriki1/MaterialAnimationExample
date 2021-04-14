package com.example.materialanimationexample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.materialanimationexample.MainViewModel
import com.example.materialanimationexample.R
import com.example.materialanimationexample.databinding.HomeFragmentLayoutBinding
import com.example.materialanimationexample.fragment.home.HomeFragmentViewModel

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: HomeFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private var viewModel: HomeFragmentViewModel? = null
    private var mainViewModel: MainViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentLayoutBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel?.setEnableDrawerLayout(true)
        binding.settingBtn.setOnClickListener(this)
        binding.cleanBtn.setOnClickListener(this)
        binding.historyBtn.setOnClickListener(this)
        binding.searchBtn.setOnClickListener(this)
        binding.storageBtn.setOnClickListener(this)
        activeBtn(binding.historyBtn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.settingBtn -> {
                mainViewModel?.setOpenDrawerLayout(true)
            }
            binding.cleanBtn -> {
               activeBtn(binding.cleanBtn)
            }
            binding.historyBtn -> {
                activeBtn(binding.historyBtn)
            }
            binding.searchBtn -> {
                goToSearchScreen()
            }
            binding.storageBtn -> {
                activeBtn(binding.storageBtn)
            }
        }
    }

    private fun goToSearchScreen() {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
    }

    private fun activeBtn(view: View) {
        binding.historyBtn.isActivated = false
        binding.cleanBtn.isActivated = false
        binding.storageBtn.isActivated = false
        view.isActivated = true
    }


}