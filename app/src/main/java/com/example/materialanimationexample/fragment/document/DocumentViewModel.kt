package com.example.materialanimationexample.fragment.document

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

class DocumentViewModelFactory(val context: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DocumentViewModelFactory::class.java)) {
            return DocumentViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DocumentViewModel(val context: Application) : ViewModel() {
    private var _listImage = MutableLiveData<List<DocumentFile>>()
    val listImage get() = _listImage

    init {
        viewModelScope.launch {
            _listImage.value = context.queryFileImage()
        }
    }
}