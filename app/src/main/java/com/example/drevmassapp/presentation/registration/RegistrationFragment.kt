package com.example.drevmassapp.presentation.registration

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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentRegistrationBinding
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var _binding: FragmentRegistrationBinding
    private val binding get() = _binding
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.apply {
            setupToolbar()
            setupEditText(etName, vName)
            setupEditText(etEmail, vEmail)
            setupEditText(etPhone, vPhone)
            setupPasswordField()
            setupEditTexts()
            setupObservers()

            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(binding.etPassword)
                }
                true
            }

            root.setOnClickListener {
                hideKeyboard(etPassword)
            }
            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }

            btnRegistration.setOnClickListener {
                validateFields()
            }

        }
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                progressBar.isIndeterminate = true
                btnRegistration.text = if (isLoading) "" else getString(R.string.registration)
            }
            viewModel.registration.observe(viewLifecycleOwner) { response ->
                response?.message?.let { message ->
                    if (message.isNotEmpty()) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        Log.d("RegistrationFragment", "Registration: $message")
                    }
                } ?: run {
                    ivBackground.flNotification.visibility = View.VISIBLE
                }
            }
            viewModel.error.observe(viewLifecycleOwner) { error ->
                if (error.isNullOrEmpty()) setSuccess() else setError(error.toString())
            }
        }
    }

    private fun validateFields() {
        if (!checkFieldsForEmptyValues()) return
        hideKeyboard(binding.etPassword)
        viewModel.registration(
            binding.etEmail.text.toString(),
            binding.etName.text.toString(),
            binding.etPassword.text.toString(),
            binding.etPhone.text.toString(),
        )
        if (checkName() && checkPhone() && checkEmail() && checkPassword()) setSuccess() else setError("Неверно введены данные")
    }

    private fun setSuccess() {
        binding.toolbarContainer.visibility = View.VISIBLE
        binding.ivBackground.flNotification.visibility = View.GONE
        setField(
            binding.ivName,
            binding.vName,
            R.drawable.ic_profile_24,
            R.drawable.background_credentials_unfocus
        )
        setField(
            binding.ivPhone,
            binding.vPhone,
            R.drawable.ic_phone_24,
            R.drawable.background_credentials_unfocus
        )
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

    private fun setError(error: String) {
        binding.toolbarContainer.visibility = View.GONE
        binding.ivBackground.flNotification.visibility = View.VISIBLE
        binding.ivBackground.tvMessage.text = error
        setField(
            binding.ivName,
            binding.vName,
            R.drawable.ic_profile_error_24,
            R.drawable.background_credentials_error
        )
        setField(
            binding.ivPhone,
            binding.vPhone,
            R.drawable.ic_phone_error_24,
            R.drawable.background_credentials_error
        )
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

    private fun checkName(): Boolean {
        return binding.etName.text.toString().length >= 6
    }

    private fun checkPhone(): Boolean {
        return binding.etPhone.text.toString().length >= 10
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

    private fun hideKeyboard(view: View) {
        binding.etPassword.clearFocus()
        binding.etName.clearFocus()
        binding.etEmail.clearFocus()
        binding.etPhone.clearFocus()
        setButtonMargin(64)
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setButtonMargin(int: Int) {
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

    private fun checkFieldsForEmptyValues(): Boolean {
        val isAllFieldsFilled =
            binding.etName.text!!.isNotEmpty() && binding.etEmail.text!!.isNotEmpty() && binding.etPhone.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()

        if (isAllFieldsFilled) {
            binding.btnRegistration.setBackgroundResource(R.drawable.background_btn_50_able)
        } else {
            binding.btnRegistration.setBackgroundResource(R.drawable.background_btn_50_disabled)
        }
        return isAllFieldsFilled
    }

    private fun setFocusChange(editText: EditText, backgroundView: View, isPassword: Boolean) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_focus)
                setButtonMargin(820)
            } else {
                backgroundView.setBackgroundResource(R.drawable.background_credentials_unfocus)
                if(!isPassword) editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                hideKeyboard(editText)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupEditText(editText: EditText, backgroundView: View) {
        editText.apply {
            setFocusChange(this, backgroundView, false)
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

    private fun setupEditTexts() {
        val editTexts = listOf(binding.etName, binding.etEmail, binding.etPhone, binding.etPassword)

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

            setFocusChange(this, binding.vPassword, true)
        }
    }
}
