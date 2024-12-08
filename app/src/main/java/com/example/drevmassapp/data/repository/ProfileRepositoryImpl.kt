package com.example.drevmassapp.data.repository

import android.content.Context
import android.util.Log
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.ResetPasswordModel
import com.example.drevmassapp.domain.model.UserInformationModel
import com.example.drevmassapp.domain.model.UserResponse
import com.example.drevmassapp.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val context: Context): ProfileRepository  {

    private val apiService = ServiceBuilder.buildService(ApiService::class.java)
    private val preferencesRepository = PreferencesRepositoryImpl(PreferencesManager(context))
    private val token = "Bearer ${preferencesRepository.getUserToken()}"


    override suspend fun getUser(): UserResponse {
        val response = apiService.getUser(token)
        Log.d("ProfileRepository", "getUser: $response")
        return response
    }

    override suspend fun deleteUser(): ForgotModel {
        val response = apiService.deleteUser(token)
        Log.d("ProfileRepository", "deleteUser: $response")
        return response
    }

    override suspend fun getUserInformation(): UserInformationModel {
        val response = apiService.getUserInformation(token)
        Log.d("ProfileRepository", "getUserInfo: $response")
        return response
    }

    override suspend fun setUserInformation(userInfo: UserInformationModel): UserInformationModel {
        val response = apiService.setUserInformation(userInfo, token)
        Log.d("ProfileRepository", "setUserInfo: $response")
        return response
    }

    override suspend fun resetPassword(resetPassword: ResetPasswordModel): ForgotModel {
        val response = apiService.resetPassword(resetPassword, token)
        Log.d("ProfileRepository", "resetPassword: $response")
        return response
    }
}