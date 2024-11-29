package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class CourseByIdResponse(
    @SerializedName("all_lessons")
    val allLessons: Int, // 12
    @SerializedName("completed_lessons")
    val completedLessons: Int, // 0
    @SerializedName("course")
    val course: Course
) {
    data class Course(
        @SerializedName("bonus_info")
        val bonusInfo: BonusInfo,
        @SerializedName("bonus_type")
        val bonusType: String, // course
        @SerializedName("completed")
        val completed: Boolean, // false
        @SerializedName("description")
        val description: String, // Курс «Основы здорового позвоночника» представляет собой комплекс упражнений по укреплению здоровья вашей спине
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
        @SerializedName("lessons")
        val lessons: List<Lesson>,
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

        data class Lesson(
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
            val orderId: Int, // 1
            @SerializedName("title")
            val title: String, // Деревянный роликовый тренажер-массажер для спины Древмасс. Полная комплектация и сборка массажера.
            @SerializedName("used_products")
            val usedProducts: Any, // null
            @SerializedName("video_src")
            val videoSrc: String // 7ujyObMRlAE
        )
    }
}