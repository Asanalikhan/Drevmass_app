package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("message")
    val message: String
)