package com.example.materialanimationexample.fragment.image

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.materialanimationexample.databinding.StorageFragmentLayoutBinding
import com.example.materialanimationexample.fragment.dialog.DialogActionItem
import com.example.materialanimationexample.fragment.dialog.DialogDetailDocumentFile
import com.example.materialanimationexample.fragment.dialog.OnActionItemListener

class ImageFragment : Fragment(), onLongClickItemImage, OnActionItemListener {
    var adapterImage : AdapterImage? = null
    var documentItem : DocumentFile? = null
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
        adapterImage = AdapterImage(this@ImageFragment)
        adapter = adapterImage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLongClickImage(documentFile: DocumentFile) {
        this.documentItem = documentFile
        val dialogActionItem = DialogActionItem()
        dialogActionItem.setListener(this)
        dialogActionItem.show(childFragmentManager,DialogActionItem::class.java.name)
    }

    override fun share() {
        val itemDocument = documentItem ?: return
        val itemType = itemDocument.type ?: "*/*"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = itemType
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(Intent.EXTRA_STREAM, itemDocument.uri)
        }
        startActivity(Intent.createChooser(shareIntent, "Share item"))
    }

    override fun move() {

    }

    override fun delete() {

    }

    override fun copy() {
    }

    override fun rename() {
    }

    override fun detail() {
        val itemDocument = documentItem ?: return
        findNavController().navigate(ImageFragmentDirections.actionImageFragmentToDialogDetailDocumentFile(itemDocument.uri.toString()))
    }
}