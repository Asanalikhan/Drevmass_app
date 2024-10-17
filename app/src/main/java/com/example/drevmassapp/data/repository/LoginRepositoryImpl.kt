package com.example.drevmassapp.data.repository

import android.util.Log
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.domain.model.LoginModel
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.repository.LoginRepository

class LoginRepositoryImpl: LoginRepository {
    private val apiService = ServiceBuilder.buildService(ApiService::class.java)

    override suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(LoginModel("string", email, password))
        Log.d("LoginRepository", "login: $response")
        return response
    }
}