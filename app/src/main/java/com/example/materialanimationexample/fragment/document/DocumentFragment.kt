package com.example.materialanimationexample.fragment.document

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.materialanimationexample.databinding.DocumentScreenLayoutBinding
import com.example.materialanimationexample.fragment.DialogActionItem
import com.example.materialanimationexample.fragment.OnActionItemListener

class DocumentFragment : Fragment(), OnActionItemListener {

    private val navArgs : DocumentFragmentArgs by navArgs()
    private var _binding : DocumentScreenLayoutBinding? = null
    private var item : DocumentFile? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DocumentScreenLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = navArgs.uri
        uri?.let {
            item = DocumentFile.fromSingleUri(requireContext(), Uri.parse(uri))
        }
        binding.documentBtn.setOnClickListener {
            val dialogActionItem = DialogActionItem()
            dialogActionItem.setListener(this)
            dialogActionItem.show(childFragmentManager,DialogActionItem::class.java.name)
        }
    }

    override fun share() {
        val itemDocument = item ?: return
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "application/pdf"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(Intent.EXTRA_STREAM, itemDocument.uri)
        }
        startActivity(Intent.createChooser(shareIntent, "Share audio"))
    }

    override fun move() {

    }

    override fun delete() {
        val itemDocument = item ?: return
        val delete = itemDocument.delete()
        Log.d("abc","delete $delete")
        if (delete){
            Toast.makeText(requireContext(),"Delete Completed",Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }else{
            Toast.makeText(requireContext(),"Can't delete file",Toast.LENGTH_SHORT).show()
        }
    }

    override fun copy() {

    }

    override fun rename() {
    }

    override fun detail() {

    }

}