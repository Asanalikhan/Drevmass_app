package com.example.drevmassapp.domain.model


import com.google.gson.annotations.SerializedName

data class ProductByIdResponse(
    @SerializedName("Product")
    val product: Product,
    @SerializedName("Recommend")
    val recommend: List<Recommend>
) {
    data class Product(
        @SerializedName("basket_count")
        val basketCount: Int, // 0
        @SerializedName("description")
        val description: String, // Безопасный гипоаллергенный перерабатываемый АБС пластик. Дешевле в 2 раза, благодаря технологии изготовления. Подходит к любой модели Древмасс. 5 рядов шипов. Усиливает воздействие массажера на 30%. Подходит к любой деревянной модели Древмасс.
        @SerializedName("height")
        val height: String, // для всех
        @SerializedName("id")
        val id: Int, // 14
        @SerializedName("image_src")
        val imageSrc: String, // images/1690446951.png
        @SerializedName("price")
        val price: Int, // 1190
        @SerializedName("size")
        val size: String, // 12.5 x 20.5 см
        @SerializedName("title")
        val title: String, // Ролик с шипами из пластика
        @SerializedName("video_src")
        val videoSrc: String, // XzTN5-QAgzU
        @SerializedName("viewed")
        val viewed: Int // 185
    )

    data class Recommend(
        @SerializedName("basket_count")
        val basketCount: Int, // 0
        @SerializedName("description")
        val description: String, // Ролик предназначен для подготовленных людей. Хорошо подходит опытным пользователям тренажера Древмасс. Роликом за 10 минут можно эффективно и глубоко проработать ягодицы, заднюю часть бедра, стопы, спину: поясничный, грудной и шейный отдел. Для занятий рекомендуем использовать коврик для йоги и посмотреть методику по кнопке внизу.
        @SerializedName("height")
        val height: String, // для всех
        @SerializedName("id")
        val id: Int, // 32
        @SerializedName("image_src")
        val imageSrc: String, // images/1690454389.png
        @SerializedName("price")
        val price: Int, // 1590
        @SerializedName("size")
        val size: String, // 8.3 x 15 см
        @SerializedName("title")
        val title: String, // Тревел ролик Древмасс
        @SerializedName("video_src")
        val videoSrc: String // ISjBwfxw-8E
    )
}