package com.example.materialanimationexample.fragment.image

import android.app.Application
import android.content.Context
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialanimationexample.fragment.storage.StorageHighViewModel
import com.example.materialanimationexample.utils.queryFileImage
import com.example.materialanimationexample.utils.queryFileInStore
import kotlinx.coroutines.launch

class ImageViewModelFactory(val context: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ImageViewModel(val context: Application) : ViewModel() {
    private var _listImage = MutableLiveData<List<DocumentFile>>()
    val listImage get() = _listImage

    init {
        viewModelScope.launch {
            _listImage.value = context.queryFileImage()
        }
    }
}