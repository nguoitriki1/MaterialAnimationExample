package com.example.materialanimationexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _isEnableDrawerLayout = MutableLiveData<Boolean>(false)
    private var _isOpenDrawerLayout = MutableLiveData<Boolean>(false)

    fun setEnableDrawerLayout(isEnable : Boolean){
        _isEnableDrawerLayout.value = isEnable
    }

    fun setOpenDrawerLayout(isOpen : Boolean){
        _isOpenDrawerLayout.value = isOpen
    }

    fun isEnableDrawer() : LiveData<Boolean>{
        return _isEnableDrawerLayout
    }

    fun isOpenDrawer() : LiveData<Boolean>{
        return _isOpenDrawerLayout
    }

}