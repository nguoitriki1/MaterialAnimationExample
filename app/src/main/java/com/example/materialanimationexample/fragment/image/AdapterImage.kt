package com.example.materialanimationexample.fragment.image

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.materialanimationexample.R
import com.example.materialanimationexample.databinding.ItemStorageLayoutBinding
import com.example.materialanimationexample.utils.actionViewFile
import com.example.materialanimationexample.utils.loadUri

class AdapterImage(val onLongClickItemImage : onLongClickItemImage) : ListAdapter<DocumentFile, RecyclerView.ViewHolder>(ImageItemDiffUtil()) {
    val requestOptions = RequestOptions().centerCrop()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageItemViewHolder(ItemStorageLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageItemViewHolder).bind(getItem(position))
    }
    inner class ImageItemViewHolder(private val viewBinding: ItemStorageLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(documentFile : DocumentFile){
            viewBinding.iconItem.loadUri(documentFile.uri,requestOptions)
            viewBinding.nameTxt.text = documentFile.name
            viewBinding.root.setOnClickListener {
                if (!documentFile.isDirectory){
                    val documentType = documentFile.type ?: "image/*"
                    viewBinding.root.context.actionViewFile(documentFile.uri,documentType)
                }
            }
            viewBinding.root.setOnLongClickListener {
                if (!documentFile.isDirectory){
                    onLongClickItemImage.onLongClickImage(documentFile)
                }
                return@setOnLongClickListener false
            }
        }
    }
}

interface onLongClickItemImage{
    fun onLongClickImage(documentFile: DocumentFile)
}



class ImageItemDiffUtil : DiffUtil.ItemCallback<DocumentFile>(){
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