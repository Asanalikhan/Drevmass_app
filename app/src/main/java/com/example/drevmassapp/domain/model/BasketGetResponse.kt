package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class BasketGetResponse(
    @SerializedName("basket")
    val basket: List<Basket>,
    @SerializedName("basket_price")
    val basketPrice: Int, // 0
    @SerializedName("bonus")
    val bonus: Int, // 0
    @SerializedName("count_products")
    val countProducts: Int, // 0
    @SerializedName("discount")
    val discount: Int, // 0
    @SerializedName("products")
    val products: List<ProductResponse>,
    @SerializedName("total_price")
    val totalPrice: Int, // 0
    @SerializedName("used_bonus")
    val usedBonus: Int // 0
) {
    data class Basket(
        @SerializedName("count")
        var count: Int, // 0
        @SerializedName("price")
        val price: Int, // 0
        @SerializedName("product_id")
        val productId: Int, // 0
        @SerializedName("product_img")
        val productImg: String, // string
        @SerializedName("product_title")
        val productTitle: String // string
    )
}