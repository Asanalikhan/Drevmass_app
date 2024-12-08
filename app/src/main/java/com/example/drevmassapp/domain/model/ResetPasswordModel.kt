package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class ResetPasswordModel(
    @SerializedName("current_password")
    val currentPassword: String, // string
    @SerializedName("new_password")
    val newPassword: String // string
)