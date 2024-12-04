package com.example.drevmassapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.drevmassapp.domain.model.CourseByIdResponse

class LessonDiffCallback : DiffUtil.ItemCallback<CourseByIdResponse.Course.Lesson>() {
    override fun areItemsTheSame(
        oldItem: CourseByIdResponse.Course.Lesson,
        newItem: CourseByIdResponse.Course.Lesson
    ): Boolean {
        // Compare by unique ID
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CourseByIdResponse.Course.Lesson,
        newItem: CourseByIdResponse.Course.Lesson
    ): Boolean {
        // Compare the entire content
        return oldItem == newItem
    }
}
