package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email")
    val email: String, // asanalikhan.mamyrov@gmail.com
    @SerializedName("id")
    val id: Int, // 905
    @SerializedName("name")
    val name: String, // Asanalikhan
    @SerializedName("phone_number")
    val phoneNumber: String // +77056650001
)