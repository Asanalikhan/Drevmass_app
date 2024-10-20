package com.example.drevmassapp.data.repository

import android.util.Log
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LoginModel
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.repository.AuthRepository

class AuthRepositoryImpl: AuthRepository {
    private val apiService = ServiceBuilder.buildService(ApiService::class.java)

    override suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(LoginModel("string", email, password))
        Log.d("AuthRepository", "login: $response")
        return response
    }

    override suspend fun forgot(email: String): ForgotModel {
        val response = apiService.forgot(email)
        Log.d("AuthRepository", "forgot: $response")
        return response
    }
}