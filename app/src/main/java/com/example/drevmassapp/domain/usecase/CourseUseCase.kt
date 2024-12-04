package com.example.drevmassapp.domain.usecase

import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.model.FavoriteGetResponse
import com.example.drevmassapp.domain.model.FavoriteResponse
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.LessonByIdResponse
import com.example.drevmassapp.domain.repository.CourseRepository
import java.text.Normalizer.Form

class CourseUseCase(private val courseRepository: CourseRepository) {

    suspend fun getCourse(): CourseGetResponse{
        return courseRepository.getCourse()
    }

    suspend fun getCourseBonus(): CourseBonusResponse{
        return courseRepository.getBonus()
    }

    suspend fun getCourseById(course_id: Int): CourseByIdResponse{
        return courseRepository.getCourseById(course_id)
    }

    suspend fun getLessonById(id: Int, course_id: Int): LessonByIdResponse{
        return courseRepository.getLessonById(id, course_id)
    }

    suspend fun getFavorite(): List<FavoriteResponse>{
        return courseRepository.getFavorite()
    }

    suspend fun postFavorite(lesson_id: Int): ForgotModel{
        return courseRepository.postFavorite(lesson_id)
    }

    suspend fun deleteFavorite(lesson_id: Int): ForgotModel{
        return courseRepository.deleteFavorite(lesson_id)
    }
}

