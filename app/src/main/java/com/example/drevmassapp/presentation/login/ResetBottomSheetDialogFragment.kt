package com.example.drevmassapp.presentation.login

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.ResetBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ResetBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private var _binding: ResetBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                setupFullHeight(it)
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
        _binding = ResetBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setupEditText(etEmail)
            toolbar.icBtnInfo.visibility = View.GONE
            toolbar.tvToolbarTitle.text = getString(R.string.reset_password)
            toolbar.tvToolbarTitle.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_dark_1000
                )
            )

            toolbar.icBtnBack.setOnClickListener {
                dismiss()
            }

            root.setOnClickListener{
                hideKeyboard(etEmail)
            }
            etEmail.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(binding.etEmail)
                }
                true
            }

            btnResetPassword.setOnClickListener {
                if(checkFieldsForEmptyValues()) {
                    val confirmBottomSheet = ConfirmBottomSheetDialog()
                    confirmBottomSheet.show(parentFragmentManager, "ConfirmBottomSheetDialogFragment")
                }
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditText(editText: EditText) {
        editText.apply {
            setFocusChange(this, binding.vEmail)
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val clearIcon = compoundDrawables[2]
                    clearIcon?.let {
                        val clearIconStart = width - paddingEnd - it.intrinsicWidth
                        if (event.rawX >= clearIconStart) {
                            text?.clear()
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }

            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val rightDrawable = if (s?.isNotEmpty() == true) {
                        R.drawable.ic_cancel_24
                    } else {
                        0
                    }
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, rightDrawable, 0)
                    checkFieldsForEmptyValues()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun checkFieldsForEmptyValues(): Boolean {
        val isAllFieldsFilled = binding.etEmail.text!!.isNotEmpty()

        if (isAllFieldsFilled) {
            binding.btnResetPassword.setBackgroundResource(R.drawable.background_btn_50_able)
        } else {
            binding.btnResetPassword.setBackgroundResource(R.drawable.background_btn_50_disabled)
        }
        return isAllFieldsFilled
    }

    private fun setButtonMargin(int: Int) {
        val params = binding.btnResetPassword.layoutParams as ConstraintLayout.LayoutParams
        params.bottomMargin = int
        binding.btnResetPassword.requestLayout()
    }

    private fun setFocusChange(editText: EditText, backgroundView: View) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_focus)
                binding.tvEmail.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_brand_900))
                setButtonMargin(1000)
            } else {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_unfocus)
                binding.tvEmail.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_gray_800))
                editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                hideKeyboard(editText)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        binding.etEmail.clearFocus()
        setButtonMargin(93)
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}