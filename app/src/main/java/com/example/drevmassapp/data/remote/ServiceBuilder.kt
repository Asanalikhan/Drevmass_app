package com.example.drevmassapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val BASE_URL = "http://drevmasstestapi.mobydev.kz/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getUrl(): String {
        return BASE_URL
    }
}