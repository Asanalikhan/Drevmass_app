package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class UserInformationModel(
    @SerializedName("activity")
    val activity: Int, // 0
    @SerializedName("birth")
    val birth: String,
    @SerializedName("email")
    val email: String, // asanalikhan.mamyrov@gmail.com
    @SerializedName("gender")
    val gender: Int, // 0
    @SerializedName("height")
    val height: Int, // 0
    @SerializedName("id")
    val id: Int, // 905
    @SerializedName("name")
    val name: String, // Asanalikhan
    @SerializedName("phone_number")
    val phoneNumber: String, // +77056650001
    @SerializedName("weight")
    val weight: Int // 0
)