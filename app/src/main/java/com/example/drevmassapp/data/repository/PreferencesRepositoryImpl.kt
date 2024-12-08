package com.example.drevmassapp.data.repository

import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val preferencesManager: PreferencesManager) : PreferencesRepository {

    override fun saveUserToken(token: String) {
        preferencesManager.saveString("user_token", token)
    }

    override fun getUserToken(): String {
        return preferencesManager.getString("user_token")
    }

    override fun deleteUserToken() {
        preferencesManager.remove("user_token")
    }
}