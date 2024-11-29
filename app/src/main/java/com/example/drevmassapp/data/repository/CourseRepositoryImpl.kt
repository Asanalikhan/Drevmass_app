package com.example.drevmassapp.data.repository

import android.content.Context
import android.util.Log
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.repository.CourseRepository
import kotlinx.coroutines.processNextEventInCurrentThread

class CourseRepositoryImpl(private val context: Context) : CourseRepository {

    private val apiService = ServiceBuilder.buildService(ApiService::class.java)
    private val preferencesRepository = PreferencesRepositoryImpl(PreferencesManager(context))

    override suspend fun getCourse(): CourseGetResponse {
        val response = apiService.getCourse("Bearer ${preferencesRepository.getUserToken()}")
        Log.d("CourseRepositoryImpl", "Response $response")
        return response
    }

    override suspend fun getBonus(): CourseBonusResponse {
        val response = apiService.getCourseBonus("Bearer ${preferencesRepository.getUserToken()}")
        Log.d("CourseRepositoryImpl", "Bonus $response")
        return response
    }
}