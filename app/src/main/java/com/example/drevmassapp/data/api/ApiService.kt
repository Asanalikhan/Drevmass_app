package com.example.drevmassapp.data.api

import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.model.BasketRequest
import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LoginModel
import com.example.drevmassapp.domain.model.LoginResponse
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.model.ProductResponse
import com.example.drevmassapp.domain.model.SignupModel
import com.example.drevmassapp.domain.model.SignupResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body request: LoginModel): LoginResponse

    @FormUrlEncoded
    @POST("api/forgot_password")
    suspend fun forgot(@Field("email") email: String): ForgotModel

    @POST("api/signup")
    suspend fun signup(@Body request: SignupModel): SignupResponse

    @GET("api/products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): List<ProductResponse>

    @GET("api/products/famous")
    suspend fun getFamousProducts(
        @Header("Authorization") token: String
    ): List<ProductResponse>

    @GET("api/products/pricedown")
    suspend fun getPriceDownProducts(
        @Header("Authorization") token: String
    ): List<ProductResponse>

    @GET("api/products/priceup")
    suspend fun getPriceUpProducts(
        @Header("Authorization") token: String
    ): List<ProductResponse>

    @GET("api/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): ProductByIdResponse

    @GET("api/basket")
    suspend fun getBasket(
        @Query("is_using") is_using: String,
        @Header("Authorization") token: String,
    ): BasketGetResponse

    @POST("api/basket")
    suspend fun addBasket(
        @Header("Authorization") token: String,
        @Body() requestBody: BasketRequest,
    ): ForgotModel

    @POST("api/decrease")
    suspend fun decreaseBasket(
        @Header("Authorization") token: String,
        @Body() requestBody: BasketRequest,
    ): ForgotModel

    @POST("api/increase")
    suspend fun increaseBasket(
        @Header("Authorization") token: String,
        @Body() requestBody: BasketRequest,
    ): ForgotModel

    @DELETE("api/basket")
    suspend fun delete(
        @Header("Authorization") token: String,
    ): ForgotModel

    @DELETE("api/basket/{id}")
    suspend fun deleteById(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
    ): ForgotModel

    @GET("api/course")
    suspend fun getCourse(
        @Header("Authorization") token: String,
    ): CourseGetResponse

    @GET("api/course/bonus")
    suspend fun getCourseBonus(
        @Header("Authorization") token: String,
    ): CourseBonusResponse

}
