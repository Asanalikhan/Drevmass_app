package com.example.drevmassapp.data.model


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("deviceToken")
    val deviceToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)