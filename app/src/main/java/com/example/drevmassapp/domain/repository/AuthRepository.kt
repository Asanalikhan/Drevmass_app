package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LoginResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun forgot(email: String): ForgotModel
}