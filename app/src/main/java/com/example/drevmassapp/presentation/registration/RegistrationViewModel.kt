package com.example.drevmassapp.presentation.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.SignupResponse
import com.example.drevmassapp.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
):  ViewModel() {

    private val _registration = MutableLiveData<SignupResponse>()
    val registration: LiveData<SignupResponse> get() = _registration

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun registration(email: String, name: String, password: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try{
                val response = authUseCase.signup(email, name, password, phoneNumber)
                _registration.postValue(response)
                _error.postValue(null)
                Log.d("RegistrationViewModel", "Registration: $response")
            }catch (e: HttpException) {
                _error.postValue("Ошибка авторизации: ${e.code()}")
                Log.e("RegistrationViewModel", "Error: ${e.message()}, Code: ${e.code()}")
            }catch (e: Exception){
                e.printStackTrace()
                _error.postValue(e.message)
            } finally {
                _loading.postValue(false)
            }
        }
    }
}