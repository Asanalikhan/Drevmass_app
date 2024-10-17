package com.example.drevmassapp.domain.repository

interface PreferencesRepository {
    fun saveUserToken(token: String)
    fun getUserToken(): String
}