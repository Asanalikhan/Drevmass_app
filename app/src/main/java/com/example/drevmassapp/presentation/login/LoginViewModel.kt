package com.example.drevmassapp.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> get() = _login

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try{
                val response = authUseCase.login(email, password)
                _login.postValue(response)
                _error.postValue(null)
            }catch (e: HttpException) {
                _error.postValue("Ошибка авторизации: ${e.code()}")
                Log.e("LoginViewModel", "Error: ${e.message()}, Code: ${e.code()}")
            }catch (e: Exception){
                e.printStackTrace()
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}