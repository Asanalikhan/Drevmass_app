package com.example.drevmassapp.presentation.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemLessonBinding
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.model.FavoriteResponse
import com.example.drevmassapp.domain.model.LessonByIdResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener
import com.example.drevmassapp.utils.FavoriteDiffCallback

class ItemBookmarkAdapter : ListAdapter<LessonByIdResponse, ItemBookmarkAdapter.ItemBookmarkViewHolder>(FavoriteDiffCallback()) {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var bookmarkClickListener: OnQuantityClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBookmarkViewHolder {
        return ItemBookmarkViewHolder(
            ItemLessonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemBookmarkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ItemBookmarkViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            holder.updateBookmarkIcon(payloads[0] as Boolean)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setProducts(list: List<LessonByIdResponse>) {
        submitList(list)
    }

    inner class ItemBookmarkViewHolder(val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: LessonByIdResponse) {
            val isFavorite = !favorite.isFavorite
            Glide.with(binding.ivLesson.context)
                .load(ServiceBuilder.getUrl() + favorite.imageSrc)
                .into(binding.ivLesson)
            binding.minutes.text = favorite.duration.toString()
            binding.countLessons.text = favorite.orderId.toString()
            binding.tvTitle.text = favorite.title
            binding.ivBookmark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_24 else R.drawable.ic_bookmark_fill_24)

            binding.ivBookmark.setOnClickListener {
                bookmarkClickListener.onQuantityChanged(1, favorite.id, false)
                val currentList = currentList.toMutableList()
                currentList.removeAt(adapterPosition)
                submitList(currentList)
            }
        }

        fun updateBookmarkIcon(isFavorite: Boolean) {
            binding.ivBookmark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnBookmarkListener(listener: OnQuantityClickListener) {
        bookmarkClickListener = listener
    }
}