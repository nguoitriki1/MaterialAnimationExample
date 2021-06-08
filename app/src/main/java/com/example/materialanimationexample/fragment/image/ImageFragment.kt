package com.example.materialanimationexample.fragment.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialanimationexample.databinding.StorageFragmentLayoutBinding
import com.example.materialanimationexample.fragment.storage.StorageHighViewModel
import com.example.materialanimationexample.fragment.storage.StorageHighViewModelFactory
import com.example.materialanimationexample.fragment.storage.adapter.StorageAdapter

class ImageFragment : Fragment() {
    var adapterImage : AdapterImage? = null
    private var _binding : StorageFragmentLayoutBinding? = null
    val binding get() = _binding!!
    private val imageViewModel: ImageViewModel by viewModels(){
        ImageViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StorageFragmentLayoutBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        imageViewModel.listImage.observe(viewLifecycleOwner, Observer {
            adapterImage?.submitList(it)
        })
    }

    private fun setupRecyclerView() = with(binding.rvStorage) {
        layoutManager = LinearLayoutManager(requireContext())
        adapterImage = AdapterImage()
        adapter = adapterImage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}