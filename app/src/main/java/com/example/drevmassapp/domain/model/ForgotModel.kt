package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class ForgotModel(
    @SerializedName("message")
    val message: String
)