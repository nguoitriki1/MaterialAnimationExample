package com.example.materialanimationexample.fragment.storage

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialanimationexample.databinding.StorageFragmentLayoutBinding
import com.example.materialanimationexample.fragment.storage.adapter.StorageAdapter
import com.example.materialanimationexample.utils.PreferencesHelper
import com.example.materialanimationexample.utils.getUriFromAction
import com.example.materialanimationexample.utils.requestPermission

class StorageHighFragment : Fragment() {
    private var _binding : StorageFragmentLayoutBinding? = null
    val binding get() = _binding!!
    private val highViewModel: StorageHighViewModel by viewModels(){
        StorageHighViewModelFactory(requireActivity().application)
    }
    private var adapterRv : StorageAdapter? = null

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (Build.VERSION.SDK_INT > 28) {
                val data = result.data ?: return@registerForActivityResult
                val uri = data.data ?: return@registerForActivityResult
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                highViewModel.setUriStorage(uri)
                val fromTreeUri = DocumentFile.fromTreeUri(requireContext(), uri)
                binding.choiceFolderBtn.text = fromTreeUri?.name
                binding.path.text = fromTreeUri?.name
                PreferencesHelper.putString("sting",uri.toString())
            }
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
        getOldData()
        setupRecyclerView()
        highViewModel.listData.observe(viewLifecycleOwner, Observer {
            adapterRv?.submitList(it)
        })
        binding.choiceFolderBtn.setOnClickListener {
            requireContext().getUriFromAction(startForResult)
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupRecyclerView() = with(binding.rvStorage) {
        layoutManager = LinearLayoutManager(requireContext())
        adapterRv = StorageAdapter(highViewModel)
        adapter = adapterRv
    }

    private fun getOldData() {
        val string = PreferencesHelper.getString("sting", "")
        if (string.isEmpty()){
            requireActivity().requestPermission(startForResult)
        }else{
            val fromTreeUri = DocumentFile.fromTreeUri(requireContext(), Uri.parse(string)) ?: return
            if (fromTreeUri.canWrite()){
                binding.choiceFolderBtn.text = fromTreeUri.name
                binding.path.text = fromTreeUri.name
                highViewModel.setUriStorage(Uri.parse(string))
            }else{
                requireActivity().requestPermission(startForResult)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}