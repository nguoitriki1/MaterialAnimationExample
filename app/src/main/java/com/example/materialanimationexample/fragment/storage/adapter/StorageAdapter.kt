package com.example.materialanimationexample.fragment.storage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.materialanimationexample.R
import com.example.materialanimationexample.databinding.ItemStorageLayoutBinding
import com.example.materialanimationexample.fragment.storage.StorageHighViewModel

class StorageAdapter(val viewModel: StorageHighViewModel) : ListAdapter<DocumentFile, RecyclerView.ViewHolder>(StorageItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StorageItemViewHolder(ItemStorageLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StorageItemViewHolder).bind(getItem(position))
    }
    inner class StorageItemViewHolder(private val viewBinding: ItemStorageLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(documentFile : DocumentFile){
            if (documentFile.isDirectory){
                viewBinding.iconItem.setImageResource( R.drawable.folder_orange_icon)
            }else{
                viewBinding.iconItem.setImageResource( R.drawable.file_document_icon)
            }
            viewBinding.nameTxt.text = documentFile.name
            viewBinding.root.setOnClickListener {
                if (documentFile.isDirectory){
                    viewModel.openFile(documentFile)
                }else{
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    val data = documentFile.uri
                    intent.setDataAndType(data, documentFile.type)
                    viewBinding.root.context.startActivity(Intent.createChooser(intent, "view action"))
                }
            }
        }
    }
}



class StorageItemDiffUtil : DiffUtil.ItemCallback<DocumentFile>(){
    override fun areItemsTheSame(
        oldItem: DocumentFile,
        newItem: DocumentFile
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: DocumentFile,
        newItem: DocumentFile
    ): Boolean {
        return true
    }

}