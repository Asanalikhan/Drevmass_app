package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("course_id")
    val courseId: Int, // 21
    @SerializedName("course_name")
    val courseName: String, // Внедрение занятий на Древмасс за 21 день
    @SerializedName("lessons")
    val lessons: List<LessonByIdResponse>
)