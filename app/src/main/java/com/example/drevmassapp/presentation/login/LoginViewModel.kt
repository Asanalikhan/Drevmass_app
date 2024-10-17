package com.example.drevmassapp.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.repository.LoginRepository
import com.example.drevmassapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> get() = _login

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = loginUseCase.login(email, password)
                _login.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}