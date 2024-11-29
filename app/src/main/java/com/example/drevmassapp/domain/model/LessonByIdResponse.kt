package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class LessonByIdResponse(
    @SerializedName("completed")
    val completed: Boolean, // false
    @SerializedName("CourseID")
    val courseID: Int, // 0
    @SerializedName("CourseTitle")
    val courseTitle: String,
    @SerializedName("description")
    val description: String, // Полная комплектация и сборка. Полная комплектация массажера Древмасс включает в себя: инструкцию, гарантийный талон, набор креплений, фиксатор для вертикального хранения, медицинский массажер Су Джок, воск для смазки осей, браслет на руку, тряпочка для протирки.
    @SerializedName("duration")
    val duration: Int, // 333
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("image_src")
    val imageSrc: String, // images/1690443864.png
    @SerializedName("is_favorite")
    val isFavorite: Boolean, // false
    @SerializedName("name")
    val name: String,
    @SerializedName("order_id")
    val orderId: Int, // 0
    @SerializedName("title")
    val title: String, // Деревянный роликовый тренажер-массажер для спины Древмасс. Полная комплектация и сборка массажера.
    @SerializedName("used_products")
    val usedProducts: List<ProductResponse>,
    @SerializedName("video_src")
    val videoSrc: String // 7ujyObMRlAE
)