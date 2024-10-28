package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.model.SignupResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun forgot(email: String): ForgotModel
    suspend fun signup(email: String, name: String, password:String, phoneNumber: String): SignupResponse
}