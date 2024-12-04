package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

class FavoriteGetResponse : ArrayList<FavoriteGetResponse.FavoriteGetResponseItem>(){
    data class FavoriteGetResponseItem(
        @SerializedName("course_id")
        val courseId: Int,
        @SerializedName("course_name")
        val courseName: String,
        @SerializedName("lessons")
        val lessons: List<LessonByIdResponse>
    )
}