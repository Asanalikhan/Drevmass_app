package com.example.drevmassapp.data.repository

import android.content.Context
import android.util.Log
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.model.FavoriteResponse
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LessonByIdResponse
import com.example.drevmassapp.domain.repository.CourseRepository

class CourseRepositoryImpl(private val context: Context) : CourseRepository {

    private val apiService = ServiceBuilder.buildService(ApiService::class.java)
    private val preferencesRepository = PreferencesRepositoryImpl(PreferencesManager(context))
    private val token = "Bearer ${preferencesRepository.getUserToken()}"

    override suspend fun getCourse(): CourseGetResponse {
        val response = apiService.getCourse(token)
        Log.d("CourseRepositoryImpl", "Response $response")
        return response
    }

    override suspend fun getBonus(): CourseBonusResponse {
        val response = apiService.getCourseBonus(token)
        Log.d("CourseRepositoryImpl", "Bonus $response")
        return response
    }

    override suspend fun getCourseById(courseId: Int): CourseByIdResponse {
        val response = apiService.getCourseById(courseId, token)
        Log.d("CourseRepositoryImpl", "CourseById $response")
        return response
    }

    override suspend fun getLessonById(id: Int, course_id: Int): LessonByIdResponse {
        val response = apiService.getLessonById(id, course_id, token)
        Log.d("CourseRepositoryImpl", "LessonById $response")
        return response
    }

    override suspend fun getFavorite(): List<FavoriteResponse> {
        val response = apiService.getFavorites(token)
        Log.d("CourseRepositoryImpl", "Favorites: $response")
        return response
    }

    override suspend fun postFavorite(lesson_id: Int): ForgotModel {
        val response = apiService.postFavorite(lesson_id, token)
        Log.d("CourseRepositoryImpl", "Post Favorite: $response")
        return response
    }

    override suspend fun deleteFavorite(lesson_id: Int): ForgotModel {
        val response = apiService.deleteFavorite(lesson_id, token)
        Log.d("CourseRepositoryImpl", "Delete Favorite: $response")
        return response
    }
}