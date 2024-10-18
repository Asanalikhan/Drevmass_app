package com.example.drevmassapp.presentation.login

import android.annotation.SuppressLint
import android.content.Context
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
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentLoginBinding
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var progressBar: CircularProgressIndicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        progressBar = binding.progressBar
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setListeners()
        setupObservers()
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                progressBar.isIndeterminate = true
                btnLogin.text = if (isLoading) "" else getString(R.string.to_login)
            }
            viewModel.error.observe(viewLifecycleOwner) { error ->
                if (error.isNullOrEmpty()) setSuccess() else setError()
            }
            viewModel.login.observe(viewLifecycleOwner) { response ->
                if (response.accessToken.isNotEmpty()) {
                    findNavController().navigate(R.id.action_loginFragment_to_courseFragment)
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            setupEditText(etEmail)
            setupPasswordField()
            setupEditTexts()
            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                }
                true
            }

            root.setOnClickListener {
                hideKeyboard()
            }

            tvRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            btnLogin.setOnClickListener {
                validateFields()
            }
            tvForgotPassword.setOnClickListener {
                val resetBottomSheet = ResetBottomSheetDialogFragment()
                resetBottomSheet.show(parentFragmentManager, "ResetBottomSheetDialogFragment")
            }
        }
    }

    private fun validateFields() {
        if (!checkFieldsForEmptyValues()) return
        hideKeyboard()
        if (checkEmail() && checkPassword()) setSuccess() else setError()
        viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
    }

    private fun setError() {
        binding.toolbarContainer.visibility = View.GONE
        binding.ivBackground.flNotification.visibility = View.VISIBLE
        setField(
            binding.ivEmail,
            binding.vEmail,
            R.drawable.ic_mail_error_24,
            R.drawable.background_credentials_error
        )
        setField(
            binding.ivPassword,
            binding.vPassword,
            R.drawable.ic_lock_error_24,
            R.drawable.background_credentials_error
        )
    }

    private fun setSuccess() {
        binding.toolbarContainer.visibility = View.VISIBLE
        binding.ivBackground.flNotification.visibility = View.GONE
        setField(
            binding.ivEmail,
            binding.vEmail,
            R.drawable.ic_mail_24,
            R.drawable.background_credentials_unfocus
        )
        setField(
            binding.ivPassword,
            binding.vPassword,
            R.drawable.ic_lock_24,
            R.drawable.background_credentials_unfocus
        )
    }

    private fun checkEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
            .matches()
    }

    private fun checkPassword(): Boolean {
        return binding.etPassword.text.toString().length >= 6
    }

    private fun setField(imageView: ImageView, backgroundView: View, src: Int, drawable: Int) {
        imageView.setImageResource(src)
        backgroundView.setBackgroundResource(drawable)
    }

    private fun hideKeyboard() {
        binding.etPassword.clearFocus()
        binding.etEmail.clearFocus()
        setButtonMargin(64)
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            tvToolbarTitle.visibility = View.GONE
            icBtnInfo.visibility = View.GONE
            icBtnBack.setOnClickListener { requireActivity().onBackPressed() }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditText(editText: EditText) {
        editText.apply {
            setFocusChange(this, binding.vEmail, false)
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

    private fun setFocusChange(editText: EditText, backgroundView: View, isPassword: Boolean) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_focus)
                setButtonMargin(820)
            } else {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_unfocus)
                if (!isPassword) editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                hideKeyboard()
            }
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
                                null, null, ContextCompat.getDrawable(
                                    requireContext(),
                                    if (isPasswordVisible) R.drawable.ic_show_off_24 else R.drawable.ic_show_24
                                ), null
                            )
                            setSelection(text!!.length)
                            return@setOnTouchListener true
                        }
                    }
                }
                false
            }

            setFocusChange(this, binding.vPassword, true)
        }
    }

    private fun setupEditTexts() {
        val editTexts = listOf(binding.etEmail, binding.etPassword)

        editTexts.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
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
        val isAllFieldsFilled =
            binding.etEmail.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()

        if (isAllFieldsFilled) {
            binding.btnLogin.setBackgroundResource(R.drawable.background_btn_50_able)
        } else {
            binding.btnLogin.setBackgroundResource(R.drawable.background_btn_50_disabled)
        }
        return isAllFieldsFilled
    }
}
