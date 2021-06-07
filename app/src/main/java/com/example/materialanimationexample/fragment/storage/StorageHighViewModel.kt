package com.example.materialanimationexample.fragment.storage

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.*
import com.example.materialanimationexample.utils.PreferencesHelper


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
    private var _uriStorage: MutableLiveData<Uri?> = MutableLiveData<Uri?>()
    val listData: LiveData<List<DocumentFile>>
        get() = _uriStorage.map {
            val uri = it ?: return@map emptyList<DocumentFile>()
            val newList: MutableList<DocumentFile> = ArrayList()
            val fromTreeUri = DocumentFile.fromTreeUri(context, uri) ?: return@map newList
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
            newList
        }

    fun setUriStorage(uri: Uri?) {
        _uriStorage.value = uri
        PreferencesHelper.putString("sting",uri.toString())
    }


}