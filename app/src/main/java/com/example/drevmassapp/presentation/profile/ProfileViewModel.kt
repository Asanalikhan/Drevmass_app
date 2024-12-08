package com.example.drevmassapp.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.ResetPasswordModel
import com.example.drevmassapp.domain.model.UserInformationModel
import com.example.drevmassapp.domain.model.UserResponse
import com.example.drevmassapp.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel() {

    private var _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> get() = _user

    private var _delete = MutableLiveData<ForgotModel>()
    val delete: LiveData<ForgotModel> get() = _delete

    private var _userInformation = MutableLiveData<UserInformationModel>()
    val userInformation: LiveData<UserInformationModel> get() = _userInformation

    private var _reset = MutableLiveData<ForgotModel>()
    val reset: LiveData<ForgotModel> get() = _reset

    private var _setResponse = MutableLiveData<UserInformationModel>()
    val setResponse: LiveData<UserInformationModel> get() = _setResponse

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val response = useCase.getUser()
                _user.postValue(response)
                _error.postValue(null)
                Log.d("ProfileViewModel", "$response")
            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.d("ProfileViewModel", "${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val response = useCase.deleteUser()
                _delete.postValue(response)
                _error.postValue(null)
                Log.d("ProfileViewModel", "$response")
            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.d("ProfileViewModel", "${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun getUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val response = useCase.getUserInformation()
                _userInformation.postValue(response)
                _error.postValue(null)
                Log.d("ProfileViewModel", "$response")
            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.d("ProfileViewModel", "${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun setUserInformation(user: UserInformationModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val response = useCase.setUserInformation(user)
                _setResponse.postValue(response)
                _error.postValue(null)
                Log.d("ProfileViewModel", "$response")
            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.d("ProfileViewModel", "${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun resetPassword(resetPassword: ResetPasswordModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                val response = useCase.resetPassword(resetPassword)
                _reset.postValue(response)
                _error.postValue(null)
                Log.d("ProfileViewModel","resetPassword: $response")
            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.d("ProfileViewModel", "${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

}