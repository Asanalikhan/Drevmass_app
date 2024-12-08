package com.example.drevmassapp.presentation.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.BottomSheetCallBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CallFragmentDialog: BottomSheetDialogFragment() {

    private lateinit var _binding: BottomSheetCallBinding
    private val binding get() = _binding
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var clipData: ClipData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCallBinding.inflate(layoutInflater, container, false)
        copyNumber()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(true)

        binding.canselContactPhoneNumber.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialog

    private fun copyNumber() {
        clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        binding.contactPhoneNumber.setOnClickListener {
            clipData = ClipData.newPlainText("text", binding.contactPhoneNumber.text)
            clipboardManager.setPrimaryClip(clipData)
        }
    }
}