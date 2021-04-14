package com.example.materialanimationexample.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.materialanimationexample.databinding.DetailHomeLayoutBinding

class DetailHomeFragment : Fragment() {
    private var _binding : DetailHomeLayoutBinding? = null
    private val binding get() = _binding!!
    private var viewModel : DetailHomeViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailHomeLayoutBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(DetailHomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}