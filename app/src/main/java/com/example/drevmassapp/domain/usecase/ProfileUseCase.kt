package com.example.drevmassapp.domain.usecase

import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.ResetPasswordModel
import com.example.drevmassapp.domain.model.UserInformationModel
import com.example.drevmassapp.domain.model.UserResponse
import com.example.drevmassapp.domain.repository.ProfileRepository

class ProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend fun getUser(): UserResponse {
        return profileRepository.getUser()
    }

    suspend fun deleteUser(): ForgotModel {
        return profileRepository.deleteUser()
    }

    suspend fun getUserInformation(): UserInformationModel {
        return profileRepository.getUserInformation()
    }

    suspend fun setUserInformation(userInfo: UserInformationModel): UserInformationModel {
        return profileRepository.setUserInformation(userInfo)
    }

    suspend fun resetPassword(resetPassword: ResetPasswordModel): ForgotModel{
        return profileRepository.resetPassword(resetPassword)
    }
}
