package com.example.drevmassapp.presentation.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputType
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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setupToolbar()
            setupEditText(etEmail, R.drawable.ic_mail_24, vEmail)
            setupPasswordField()

            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) { hideKeyboard(etPassword) }
                true
            }

            root.setOnClickListener {
                hideKeyboard(etPassword)
            }

            tvRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        binding.etPassword.clearFocus()
        setButtonMargin(24)
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            tvToolbarTitle.visibility = View.GONE
            icBtnInfo.visibility = View.GONE
            icBtnBack.setOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun checkFieldsForEmptyValues() {
        val isAllFieldsFilled = binding.etEmail.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()
        if (isAllFieldsFilled) {
            binding.btnLogin.setBackgroundResource(R.drawable.background_btn_50_able)
        } else {
            binding.btnLogin.setBackgroundResource(R.drawable.background_btn_50_disabled)
        }
    }

    private fun setFocusChange(editText: EditText, backgroundView: View) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_focus)
                setButtonMargin(820)
            } else {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_unfocus)
                hideKeyboard(editText)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditText(editText: EditText, leftDrawable: Int, backgroundView: View) {
        editText.apply {
            setFocusChange(this, backgroundView)
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) { checkFieldsForEmptyValues() }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0)
        }
    }

    private fun setButtonMargin(int: Int) {
        val params = binding.btnLogin.layoutParams as ConstraintLayout.LayoutParams
        params.bottomMargin = int
        binding.btnLogin.requestLayout()
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
                                ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock_24),
                                null,
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    if (isPasswordVisible) R.drawable.ic_show_off_24 else R.drawable.ic_show_24
                                ),
                                null
                            )
                            setSelection(text!!.length)
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }

            setFocusChange(this, binding.vPassword)
        }
    }
}
