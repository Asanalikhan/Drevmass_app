package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

class FavoriteGetResponse : ArrayList<FavoriteGetResponse.FavoriteGetResponseItem>(){
    data class FavoriteGetResponseItem(
        @SerializedName("course_id")
        val courseId: Int, // 0
        @SerializedName("course_name")
        val courseName: String, // string
        @SerializedName("lessons")
        val lessons: List<Lesson>
    ) {
        data class Lesson(
            @SerializedName("completed")
            val completed: Boolean, // true
            @SerializedName("courseID")
            val courseID: Int, // 0
            @SerializedName("courseTitle")
            val courseTitle: String, // string
            @SerializedName("description")
            val description: String, // string
            @SerializedName("duration")
            val duration: Int, // 0
            @SerializedName("id")
            val id: Int, // 0
            @SerializedName("image_src")
            val imageSrc: String, // string
            @SerializedName("is_favorite")
            val isFavorite: Boolean, // true
            @SerializedName("name")
            val name: String, // string
            @SerializedName("order_id")
            val orderId: Int, // 0
            @SerializedName("title")
            val title: String, // string
            @SerializedName("used_products")
            val usedProducts: List<UsedProduct>,
            @SerializedName("video_src")
            val videoSrc: String // string
        ) {
            data class UsedProduct(
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
    }
}