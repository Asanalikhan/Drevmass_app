package com.example.drevmassapp.data.api

import com.example.drevmassapp.data.model.LoginModel
import com.example.drevmassapp.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body request: LoginModel): LoginResponse

}
