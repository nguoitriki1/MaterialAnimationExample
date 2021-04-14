package com.example.materialanimationexample.fragment.splash

import android.app.Application
import android.content.Context
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.materialanimationexample.fragment.KEY_FIRST_OPEN_APP
import com.example.materialanimationexample.fragment.PREFERENCE_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var isFirstOpenApp = false
    private var _navigateHomeScreen :MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        isFirstOpenApp = application.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE).getBoolean(KEY_FIRST_OPEN_APP,false)
    }


    fun isFirstOpenApp() : Int{
        var viewVisibility = View.VISIBLE
        if (isFirstOpenApp){
            viewVisibility = View.GONE
            viewModelScope.launch {
                delay(3000)
                _navigateHomeScreen.value = true
            }
        }
        return viewVisibility
    }

    fun isNavigationHome() : LiveData<Boolean>{
        return _navigateHomeScreen
    }

}