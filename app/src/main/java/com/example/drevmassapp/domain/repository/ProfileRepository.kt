package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.ResetPasswordModel
import com.example.drevmassapp.domain.model.UserInformationModel
import com.example.drevmassapp.domain.model.UserResponse

interface ProfileRepository {
    suspend fun getUser(): UserResponse
    suspend fun deleteUser(): ForgotModel
    suspend fun getUserInformation(): UserInformationModel
    suspend fun setUserInformation(userInfo: UserInformationModel): UserInformationModel
    suspend fun resetPassword(resetPassword: ResetPasswordModel): ForgotModel
}