package com.example.drevmassapp.domain.usecase

import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.repository.AuthRepository

class AuthUseCase(private val authRepository: AuthRepository) {
    suspend fun login(email: String, password: String): LoginResponse {
        return authRepository.login(email, password)
    }

    suspend fun forgot(email: String): ForgotModel {
        return authRepository.forgot(email)
    }
}