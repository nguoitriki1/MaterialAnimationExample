package com.example.materialanimationexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import com.example.materialanimationexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private var mainViewModel :MainViewModel? = null

    private val obIsEnableDrawer = Observer<Boolean>{
        if (it){
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }else{
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    private val obIsOpenDrawer = Observer<Boolean>{
        if (it){
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }else{
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel?.isEnableDrawer()?.observe(this,obIsEnableDrawer)
        mainViewModel?.isOpenDrawer()?.observe(this,obIsOpenDrawer)
    }
}