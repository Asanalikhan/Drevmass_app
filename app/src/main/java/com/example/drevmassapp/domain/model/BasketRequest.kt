package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class BasketRequest(
    @SerializedName("count")
    val count: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
)