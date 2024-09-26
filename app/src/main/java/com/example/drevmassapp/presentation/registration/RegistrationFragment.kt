package com.example.drevmassapp.presentation.registration

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var _binding: FragmentRegistrationBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupEditText(binding.etName, R.drawable.ic_profile_24, binding.vName)
        setupEditText(binding.etEmail, R.drawable.ic_mail_24, binding.vEmail)
        setupEditText(binding.etPhone, R.drawable.ic_phone_24, binding.vPhone)
        setupPasswordField()

        setButtonMargin(800)

        binding.etPassword.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                
                hideKeyboard(binding.etPassword)
            }
            true
        }

        binding.root.setOnClickListener {
            hideKeyboard(binding.etPassword)
        }
    }

    private fun hideKeyboard(view: View) {
        binding.etPassword.clearFocus()
        setButtonMargin(24)
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setButtonMargin(int: Int){
        val params = binding.btnRegistration.layoutParams as ConstraintLayout.LayoutParams
        params.bottomMargin = int
        binding.btnRegistration.requestLayout()
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            tvToolbarTitle.visibility = View.GONE
            icBtnInfo.visibility = View.GONE
            icBtnBack.setOnClickListener { requireActivity().onBackPressed() }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditText(editText: EditText, leftDrawable: Int, backgroundView: View) {
        editText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if(hasFocus){
                    backgroundView.setBackgroundResource(R.drawable.background_credentials_focus)
                    setButtonMargin(820)
                }
                else{
                    backgroundView.setBackgroundResource(R.drawable.background_credentials_unfocus)
                    hideKeyboard(editText)
                }
            }

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

            setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0)
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val rightDrawable = if (s?.isNotEmpty() == true) {
                        R.drawable.ic_cancel_24
                    } else {
                        0
                    }
                    setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, rightDrawable, 0)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordField() {
        var isPasswordVisible = false
        binding.etPassword.apply {
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val toggleIcon = compoundDrawables[2]
                    toggleIcon?.let {
                        val toggleIconStart = width - paddingEnd - it.intrinsicWidth
                        if (event.rawX >= toggleIconStart) {
                            isPasswordVisible = !isPasswordVisible
                            inputType = if (isPasswordVisible) {
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                            } else {
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            }
                            setCompoundDrawablesWithIntrinsicBounds(
                                ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock_24), null,
                                ContextCompat.getDrawable(requireContext(), if (isPasswordVisible) R.drawable.ic_show_off_24 else R.drawable.ic_show_24), null
                            )
                            setSelection(text!!.length)
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }

            setOnFocusChangeListener { _, hasFocus ->
                if(hasFocus){
                    binding.vPassword.setBackgroundResource(R.drawable.background_credentials_focus)
                    setButtonMargin(820)
                }
                else{
                    binding.vPassword.setBackgroundResource(R.drawable.background_credentials_unfocus)
                    hideKeyboard(binding.etPassword)
                }
            }
        }

    }
}
