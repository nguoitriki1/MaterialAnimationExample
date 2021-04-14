package com.example.materialanimationexample.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.materialanimationexample.MainViewModel
import com.example.materialanimationexample.databinding.SearchFragmentLayoutBinding

class SearchFragment : Fragment(){
    private var _binding : SearchFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private var mainViewModel: MainViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel?.setEnableDrawerLayout(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}