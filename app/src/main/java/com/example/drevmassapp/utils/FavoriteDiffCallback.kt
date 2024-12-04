package com.example.drevmassapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.drevmassapp.domain.model.FavoriteResponse
import com.example.drevmassapp.domain.model.LessonByIdResponse

class FavoriteDiffCallback : DiffUtil.ItemCallback<LessonByIdResponse>() {
    override fun areItemsTheSame(
        oldItem: LessonByIdResponse,
        newItem: LessonByIdResponse
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LessonByIdResponse,
        newItem: LessonByIdResponse
    ): Boolean {
        return oldItem == newItem
    }
}
