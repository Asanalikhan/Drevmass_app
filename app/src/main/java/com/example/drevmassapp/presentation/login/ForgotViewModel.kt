package com.example.drevmassapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun forgot(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authUseCase.forgot(email)
                _message.postValue(response.message)
                _error.postValue(null)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
        }
    }
}