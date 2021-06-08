package com.example.materialanimationexample.fragment.dialog

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.materialanimationexample.databinding.DialogActionItemBinding

class DialogActionItem : DialogFragment(), View.OnClickListener {
    private var _binding: DialogActionItemBinding? = null
    val binding get() = _binding!!
    private var listener: OnActionItemListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogActionItemBinding.inflate(inflater)
        return binding.root
    }

    fun setListener(listener: OnActionItemListener) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shareBtn.setOnClickListener(this)
        binding.moveBtn.setOnClickListener(this)
        binding.copyBtn.setOnClickListener(this)
        binding.deleteBtn.setOnClickListener(this)
        binding.renameBtn.setOnClickListener(this)
        binding.detailBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.shareBtn.id -> {
                dismiss()
                listener?.share()
            }
            binding.moveBtn.id -> {
                dismiss()
                listener?.move()
            }
            binding.copyBtn.id -> {
                dismiss()
                listener?.copy()
            }
            binding.deleteBtn.id -> {
                dismiss()
                listener?.delete()
            }
            binding.renameBtn.id -> {
                dismiss()
                listener?.rename()
            }
            binding.detailBtn.id -> {
                dismiss()
                listener?.detail()
            }
        }
    }
}

interface OnActionItemListener {
    fun share()
    fun move()
    fun delete()
    fun copy()
    fun rename()
    fun detail()
}