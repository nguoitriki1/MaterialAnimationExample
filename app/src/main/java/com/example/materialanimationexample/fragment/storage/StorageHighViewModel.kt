package com.example.materialanimationexample.fragment.storage

import android.R.attr.path
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StorageHighViewModelFactory(
    val context: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StorageHighViewModel::class.java)) {
            return StorageHighViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class StorageHighViewModel(val context: Application) : ViewModel() {
    private var _uriStorage: Uri? = null
    private val _listData: MutableLiveData<List<DocumentFile>> = MutableLiveData()
    val listData: LiveData<List<DocumentFile>> get() = _listData

    fun setUriStorage(uri: Uri?) {
        viewModelScope.launch {
            _uriStorage = uri ?: return@launch
            val newList: MutableList<DocumentFile> = ArrayList()
            withContext(Dispatchers.Default){
                val fromTreeUri = DocumentFile.fromTreeUri(context, uri) ?: return@withContext
                if (fromTreeUri.canWrite()){
                    if (fromTreeUri.isDirectory){
                        val listFiles = fromTreeUri.listFiles()
                        listFiles.forEach {
                            newList.add(it)
                        }
                    }else{
                        newList.add(fromTreeUri)
                    }
                }
            }
            _listData.value = newList
        }
    }

    fun openFile(documentFile: DocumentFile){
        val newList: MutableList<DocumentFile> = ArrayList()
        if (documentFile.canWrite()){
            if (documentFile.isDirectory){
                val listFiles = documentFile.listFiles()
                listFiles.forEach {
                    newList.add(it)
                }
                _listData.value = newList
            }else{
                Log.d("abc","open file")
            }
        }else{
            Log.d("abc","uri expired")
        }
    }


}