package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseGetResponse

interface CourseRepository {
    suspend fun getCourse(): CourseGetResponse
    suspend fun getBonus(): CourseBonusResponse
}