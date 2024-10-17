package com.example.drevmassapp.domain.usecase

import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.repository.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun login(email: String, password: String): LoginResponse {
        return loginRepository.login(email, password)
    }
}