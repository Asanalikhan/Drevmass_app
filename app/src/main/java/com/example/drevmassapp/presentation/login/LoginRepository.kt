package com.example.drevmassapp.presentation.login

import android.util.Log
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.api.ServiceBuilder
import com.example.drevmassapp.data.model.LoginModel
import com.example.drevmassapp.data.model.LoginResponse

class LoginRepository {

    private val apiService = ServiceBuilder.buildService(ApiService::class.java)

    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(LoginModel("string", email, password))
        Log.d("LoginRepository", "login: $response")
        return response
    }

}