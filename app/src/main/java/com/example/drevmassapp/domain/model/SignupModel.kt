package com.example.drevmassapp.domain.model

import com.google.gson.annotations.SerializedName

data class SignupModel(
    @SerializedName("deviceToken")
    val deviceToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)