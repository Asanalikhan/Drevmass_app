package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.model.LessonByIdResponse

interface CourseRepository {
    suspend fun getCourse(): CourseGetResponse
    suspend fun getBonus(): CourseBonusResponse
    suspend fun getCourseById(course_id: Int): CourseByIdResponse
    suspend fun getLessonById(id: Int, course_id: Int): LessonByIdResponse
}