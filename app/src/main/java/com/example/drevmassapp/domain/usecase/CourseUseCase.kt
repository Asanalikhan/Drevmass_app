package com.example.drevmassapp.domain.usecase

import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.repository.CourseRepository

class CourseUseCase(private val courseRepository: CourseRepository) {

    suspend fun getCourse(): CourseGetResponse{
        return courseRepository.getCourse()
    }
    suspend fun getCourseBonus(): CourseBonusResponse{
        return courseRepository.getBonus()
    }
}

