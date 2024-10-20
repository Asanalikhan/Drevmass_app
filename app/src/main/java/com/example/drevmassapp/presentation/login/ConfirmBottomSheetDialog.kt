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
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.ConfirmBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmBottomSheetDialog: BottomSheetDialogFragment() {

    interface ConfirmListener {
        fun onConfirm()
    }

    private var _binding: ConfirmBottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private var email: String = ""
    private var listener: ConfirmListener? = null

    companion object {
        private const val ARG_EMAIL = "email"

        fun newInstance(email: String): ConfirmBottomSheetDialog {
            val fragment = ConfirmBottomSheetDialog()
            val args = Bundle().apply {
                putString(ARG_EMAIL, email)
            }
            fragment.arguments = args
            return fragment
        }
    }

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

        email = arguments?.getString(ARG_EMAIL).toString()

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

        val fullText = "На почту $email мы отправили инструкцию для сброса пароля."
        val spannableString = SpannableString(fullText)

        val start = fullText.indexOf(email)

        if (start != -1) {
            val end = start + email.length

            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_gray_800)),
                0, start,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_dark_900)),
                start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_gray_800)),
                end, fullText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_gray_800)),
                0, fullText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.tvDescription.text = spannableString

        binding.btnConfirm.setOnClickListener {
            dismiss()
            listener?.onConfirm()
        }
    }

    fun setConfirmListener(listener: ConfirmListener) {
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}