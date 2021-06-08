package com.example.materialanimationexample.fragment.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.materialanimationexample.R
import com.example.materialanimationexample.databinding.DialogTutorialLayoutBinding

class DialogTutorialConfirmAndroid() : DialogFragment() {
    private var _binding : DialogTutorialLayoutBinding? = null
    val binding get() = _binding!!
    var callBack : onClickConfirmTutorial? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTutorialLayoutBinding.inflate(inflater)
        return binding.root
    }

    fun setListener(onClickConfirm : onClickConfirmTutorial){
        this.callBack = onClickConfirm
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q){
            binding.titleTxt.text = getString(R.string.storage_android10)
        }else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
            binding.titleTxt.text = getString(R.string.storage_android11)
        }

        binding.confirmBtn.setOnClickListener {
            callBack?.onConfirm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface onClickConfirmTutorial{
    fun onConfirm()
}