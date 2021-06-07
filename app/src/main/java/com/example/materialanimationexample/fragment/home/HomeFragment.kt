package com.example.materialanimationexample.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.materialanimationexample.MainViewModel
import com.example.materialanimationexample.R
import com.example.materialanimationexample.databinding.HomeFragmentLayoutBinding
import com.example.materialanimationexample.fragment.home.HomeFragmentViewModel
import com.example.materialanimationexample.fragment.storage.StorageHighViewModelFactory
import com.example.materialanimationexample.utils.requestPermission


class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: HomeFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeFragmentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.setEnableDrawerLayout(true)
        setOnClickView()
        activeBtn(binding.historyBtn)
    }

    private fun setOnClickView() = with(binding) {
        settingBtn.setOnClickListener(this@HomeFragment)
        cleanBtn.setOnClickListener(this@HomeFragment)
        historyBtn.setOnClickListener(this@HomeFragment)
        searchBtn.setOnClickListener(this@HomeFragment)
        storageBtn.setOnClickListener(this@HomeFragment)
        imageBtn.setOnClickListener(this@HomeFragment)
        videoBtn.setOnClickListener(this@HomeFragment)
        documentBtn.setOnClickListener(this@HomeFragment)
        musicBtn.setOnClickListener(this@HomeFragment)
        apkBtn.setOnClickListener(this@HomeFragment)
        downloadBtn.setOnClickListener(this@HomeFragment)
        zipBtn.setOnClickListener(this@HomeFragment)
        moreBtn.setOnClickListener(this@HomeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.settingBtn.id -> {
                mainViewModel.setOpenDrawerLayout(true)
            }
            binding.cleanBtn.id -> {
                activeBtn(binding.cleanBtn)
            }
            binding.historyBtn.id -> {
                activeBtn(binding.historyBtn)
            }
            binding.searchBtn.id -> {
                goToSearchScreen()
            }
            binding.storageBtn.id -> {
                activeBtn(binding.storageBtn)
                findNavController().navigate(R.id.action_homeFragment_to_storageFragment)
            }
            binding.imageBtn.id -> {
                openScreenImage()
            }
            binding.videoBtn.id -> {
//                openScreen(ScreenTypeClick.VIDEO)
            }
            binding.documentBtn.id -> {
                openScreenDocument()
            }
            binding.musicBtn.id -> {
//                openScreen(ScreenTypeClick.MUSIC)
            }
            binding.apkBtn.id -> {
//                openScreen(ScreenTypeClick.APK)
            }
            binding.downloadBtn.id -> {
//                openScreen(ScreenTypeClick.DOWNLOAD)
            }
            binding.zipBtn.id -> {
//                openScreen(ScreenTypeClick.ZIP)
            }
            binding.moreBtn.id -> {
//                openScreen(ScreenTypeClick.MORE)
            }
        }
    }

    private fun openScreenDocument() {

    }

    private fun openScreenImage() {

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