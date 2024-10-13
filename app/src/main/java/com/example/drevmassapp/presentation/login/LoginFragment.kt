//package com.example.drevmassapp.presentation.login
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.drawable.Drawable
//import android.os.Bundle
//import android.text.Editable
//import android.text.InputType
//import android.text.TextWatcher
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import android.view.inputmethod.EditorInfo
//import android.view.inputmethod.InputMethodManager
//import android.widget.EditText
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.drevmassapp.R
//import com.example.drevmassapp.databinding.FragmentLoginBinding
//
//class LoginFragment : Fragment() {
//
//    private lateinit var _binding: FragmentLoginBinding
//    private val binding get() = _binding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.apply {
//            setupToolbar()
//            setupEditText(etEmail, vEmail)
//            setupPasswordField()
//
//            etPassword.setOnEditorActionListener { _, actionId, _ ->
//                if (actionId == EditorInfo.IME_ACTION_DONE) { hideKeyboard(etPassword) }
//                true
//            }
//
//            root.setOnClickListener {
//                hideKeyboard(etPassword)
//            }
//
//            btnLogin.setOnClickListener {
//                checkFieldsForCorrectness()
//            }
//
//            tvRegistration.setOnClickListener {
//                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
//            }
//        }
//    }
//    private fun checkFieldsForCorrectness(){
//        if(checkFieldsForEmptyValues()){
//            if(checkEmail() && checkPassword()) {
//                findNavController().navigate(R.id.action_loginFragment_to_courseFragment)
//            }
//            else{
//                setCustomEditText(binding.etEmail, R.drawable.ic_mail_error_24, 0, binding.vEmail, R.drawable.background_credentials_error)
//                setCustomEditText(binding.etPassword, R.drawable.ic_lock_error_24, R.drawable.ic_show_24, binding.vPassword, R.drawable.background_credentials_error)
//                hideKeyboard(binding.etPassword)
//            }
//        }
//    }
//    private fun checkEmail(): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()
//    }
//    private fun checkPassword(): Boolean {
//        return binding.etPassword.text.toString().length >= 6
//    }
//
//    private fun hideKeyboard(view: View) {
//        binding.etPassword.clearFocus()
//        setButtonMargin(24)
//        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//    private fun setupToolbar() {
//        with(binding.toolbar) {
//            tvToolbarTitle.visibility = View.GONE
//            icBtnInfo.visibility = View.GONE
//            icBtnBack.setOnClickListener { requireActivity().onBackPressed() }
//        }
//    }
//
//    private fun checkFieldsForEmptyValues(): Boolean {
//        val isAllFieldsFilled = binding.etEmail.text!!.isNotEmpty() && binding.etPassword.text!!.isNotEmpty()
//        if (isAllFieldsFilled) {
//            binding.btnLogin.setBackgroundResource(R.drawable.background_btn_50_able)
//        } else {
//            binding.btnLogin.setBackgroundResource(R.drawable.background_btn_50_disabled)
//        }
//        return isAllFieldsFilled
//    }
//
//    private fun setFocusChange(editText: EditText, backgroundView: View, password: Boolean, rightDrawable: Boolean) {
//        editText.setOnFocusChangeListener { _, hasFocus ->
//            if(password){
//                setCustomEditText(editText, R.drawable.ic_lock_24, R.drawable.ic_show_24, backgroundView, if(hasFocus) R.drawable.background_credentials_focus else R.drawable.background_credentials_unfocus)
//            }
//            else{
//                if(hasFocus){
//                    setCustomEditText(editText, R.drawable.ic_mail_24, if(rightDrawable) R.drawable.ic_cancel_24 else 0, backgroundView, R.drawable.background_credentials_focus)
//                    setButtonMargin(820)
//                }else{
//                    setCustomEditText(editText, R.drawable.ic_mail_24, 0, backgroundView, R.drawable.background_credentials_unfocus)
//                    hideKeyboard(editText)
//                }
//            }
//        }
//    }
//
//    private fun setCustomEditText(editText: EditText, leftDrawable: Int, rightDrawable: Int, backgroundView: View, color: Int) {
//        editText.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0 , rightDrawable, 0)
//        backgroundView.setBackgroundResource(color)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun setupEditText(editText: EditText, backgroundView: View) {
//        editText.apply {
//            addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    val isDrawableVisible = s!!.isNotEmpty()
//                    setFocusChange(this@apply, backgroundView, false, isDrawableVisible)
//                    checkFieldsForEmptyValues()
//                }
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            })
//
//            setOnTouchListener { _, event ->
//                if (event.action == MotionEvent.ACTION_UP && compoundDrawables[2] != null) {
//                    val cancelIconStart = width - paddingEnd - compoundDrawables[2].intrinsicWidth
//                    if (event.rawX >= cancelIconStart) {
//                        text!!.clear()
//                        return@setOnTouchListener true
//                    }
//                }
//                false
//            }
//        }
//    }
//
//
//    private fun setButtonMargin(int: Int) {
//        val params = binding.btnLogin.layoutParams as ConstraintLayout.LayoutParams
//        params.bottomMargin = int
//        binding.btnLogin.requestLayout()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun setupPasswordField() {
//        var isPasswordVisible = false
//        binding.etPassword.apply {
//            setOnTouchListener { _, event ->
//                if (event.action == MotionEvent.ACTION_UP) {
//                    val toggleIcon = compoundDrawables[2]
//                    toggleIcon?.let {
//                        val toggleIconStart = width - paddingEnd - it.intrinsicWidth
//                        if (event.rawX >= toggleIconStart) {
//                            isPasswordVisible = !isPasswordVisible
//                            inputType = if (isPasswordVisible) {
//                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                            } else {
//                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//                            }
//                            setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock_24), null, ContextCompat.getDrawable(requireContext(), if (isPasswordVisible) R.drawable.ic_show_off_24 else R.drawable.ic_show_24), null)
//                            setSelection(text!!.length)
//                            return@setOnTouchListener true
//                        }
//                    }
//                }
//                false
//            }
//
//            setFocusChange(this, binding.vPassword, true, false)
//        }
//    }
//}

package com.example.drevmassapp.presentation.login

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
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
import android.widget.ImageView
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setupToolbar()
            setupEditText(etEmail)
            setupPasswordField()
            setupEditTexts()

            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(binding.etPassword)
                }
                true
            }

            root.setOnClickListener {
                hideKeyboard(etPassword)
            }

            tvRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            btnLogin.setOnClickListener {
                validateFields()
            }
        }
    }

    private fun validateFields() {
        if (checkEmail() && checkPassword()) {
            binding.toolbarContainer.visibility = View.VISIBLE
            binding.ivBackground.flNotification.visibility = View.GONE
            setField(binding.ivEmail, binding.vEmail, R.drawable.ic_mail_24, R.drawable.background_credentials_unfocus)
            setField(binding.ivPassword, binding.vPassword, R.drawable.ic_lock_24, R.drawable.background_credentials_unfocus)
        } else {
            binding.toolbarContainer.visibility = View.GONE
            binding.ivBackground.flNotification.visibility = View.VISIBLE
            setField(binding.ivEmail, binding.vEmail, R.drawable.ic_mail_error_24, R.drawable.background_credentials_error)
            setField(binding.ivPassword, binding.vPassword, R.drawable.ic_lock_error_24, R.drawable.background_credentials_error)
        }
    }

    private fun checkEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()
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
                    } else { 0 }
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, rightDrawable, 0)
                    checkFieldsForEmptyValues()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
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

            setFocusChange(this, binding.vPassword)
        }
    }

    private fun setupEditTexts() {
        val editTexts = listOf(binding.etEmail, binding.etPassword)

        editTexts.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) { checkFieldsForEmptyValues() }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
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
}


