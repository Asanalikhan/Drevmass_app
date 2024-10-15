package com.example.drevmassapp.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> get() = _login

    private val repository = LoginRepository()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.login(email, password)
            _login.postValue(response)
            Log.d("LoginViewModel", "login: $response")
        }
    }

}