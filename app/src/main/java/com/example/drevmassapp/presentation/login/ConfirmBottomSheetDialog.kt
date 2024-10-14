package com.example.drevmassapp.presentation.login

import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.ConfirmBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmBottomSheetDialog: BottomSheetDialogFragment() {

    private var _binding: ConfirmBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConfirmBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullText = "На почту kate.shel@mail.ru мы отправили инструкцию для сброса пароля."
        val email = "kate.shel@mail.ru"

        val spannableString = SpannableString(fullText)

        val start = fullText.indexOf(email)
        val end = start + email.length

        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_gray_800)), 0, start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_dark_900)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_gray_800)), end, fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvDescription.text = spannableString

        binding.btnConfirm.setOnClickListener{
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}