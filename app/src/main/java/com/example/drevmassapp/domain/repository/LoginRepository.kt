package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.LoginResponse

interface LoginRepository {
    suspend fun login(email: String, password: String): LoginResponse
}