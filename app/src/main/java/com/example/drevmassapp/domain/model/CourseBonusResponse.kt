package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class CourseBonusResponse(
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int, // 1500
    @SerializedName("promo_type")
    val promoType: String
)