package com.example.materialanimationexample.fragment.dialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.materialanimationexample.databinding.DetailFileLayoutBinding
import com.example.materialanimationexample.utils.convertFileSize
import java.io.File
import java.text.SimpleDateFormat

class DialogDetailDocumentFile() : DialogFragment() {
    private val navArgs : DialogDetailDocumentFileArgs by navArgs()
    private var _binding : DetailFileLayoutBinding? = null
    private var documentItem : DocumentFile? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFileLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")


        val uri = navArgs.uri
        documentItem = DocumentFile.fromSingleUri(requireContext(), Uri.parse(uri))
        documentItem?.let {
            binding.filenameDes.text = it.name
            val name = it.parentFile?.name ?: ""
            if (name.isNotEmpty()){
                binding.pathDes.text = "${name}${File.separator}${it.name}"
            }
            binding.sizeDes.text = convertFileSize(it.length())
            binding.modifileDes.text = sdf.format( it.lastModified())
        }
        binding.okBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}