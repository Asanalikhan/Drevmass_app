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
    val products: List<Product>,
    @SerializedName("total_price")
    val totalPrice: Int, // 0
    @SerializedName("used_bonus")
    val usedBonus: Int // 0
) {
    data class Basket(
        @SerializedName("count")
        val count: Int, // 0
        @SerializedName("price")
        val price: Int, // 0
        @SerializedName("product_id")
        val productId: Int, // 0
        @SerializedName("product_img")
        val productImg: String, // string
        @SerializedName("product_title")
        val productTitle: String // string
    )

    data class Product(
        @SerializedName("basket_count")
        val basketCount: Int, // 0
        @SerializedName("description")
        val description: String, // string
        @SerializedName("height")
        val height: String, // string
        @SerializedName("id")
        val id: Int, // 0
        @SerializedName("image_src")
        val imageSrc: String, // string
        @SerializedName("price")
        val price: Int, // 0
        @SerializedName("size")
        val size: String, // string
        @SerializedName("title")
        val title: String, // string
        @SerializedName("video_src")
        val videoSrc: String, // string
        @SerializedName("viewed")
        val viewed: Int // 0
    )
}