package com.example.drevmassapp.data.api

import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LoginModel
import com.example.drevmassapp.domain.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body request: LoginModel): LoginResponse

    @FormUrlEncoded
    @POST("api/forgot_password")
    suspend fun forgot(@Field("email") email: String): ForgotModel

}
