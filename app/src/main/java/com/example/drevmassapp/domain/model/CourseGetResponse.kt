package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

class CourseGetResponse : ArrayList<CourseGetResponse.CourseGetResponseItem>() {
    data class CourseGetResponseItem(
        @SerializedName("bonus_info")
        val bonusInfo: BonusInfo,
        @SerializedName("bonus_type")
        val bonusType: String, // course
        @SerializedName("completed")
        val completed: Boolean, // false
        @SerializedName("description")
        val description: String,
        @SerializedName("duration")
        val duration: Int, // 2871
        @SerializedName("id")
        val id: Int, // 18
        @SerializedName("image_src")
        val imageSrc: String, // images/1708955798.png
        @SerializedName("is_started")
        val isStarted: Boolean, // false
        @SerializedName("lesson_cnt")
        val lessonCnt: Int, // 12
        @SerializedName("name")
        val name: String // Авторская методика Древмасс
    ) {
        data class BonusInfo(
            @SerializedName("deadline")
            val deadline: String, // 90
            @SerializedName("description")
            val description: String, // Бонус за завершение курса
            @SerializedName("price")
            val price: Int, // 500
            @SerializedName("promo_type")
            val promoType: String // course
        )
    }
}