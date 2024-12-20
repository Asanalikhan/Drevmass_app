package com.example.drevmassapp.presentation.detailed

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemLessonBinding
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener
import com.example.drevmassapp.utils.LessonDiffCallback

class LessonAdapter : ListAdapter<CourseByIdResponse.Course.Lesson, LessonAdapter.LessonViewHolder>(LessonDiffCallback()) {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var bookmarkClickListener: OnQuantityClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(
            ItemLessonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: LessonViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            holder.updateBookmarkIcon(payloads[0] as Boolean)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setProducts(list: List<CourseByIdResponse.Course.Lesson>) {
        submitList(list)
    }

    inner class LessonViewHolder(val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CourseByIdResponse.Course.Lesson) {
            val isFavorite = !product.isFavorite
            Glide.with(binding.ivLesson.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivLesson)
            binding.minutes.text = product.duration.toString()
            binding.countLessons.text = product.orderId.toString()
            binding.tvTitle.text = product.title
            binding.ivBookmark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_white else R.drawable.ic_bookmark_white_fill)

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
            }

            binding.ivBookmark.setOnClickListener {
                bookmarkClickListener.onQuantityChanged(1, product.id, isFavorite)
                Log.d("LessonAdapter", "$isFavorite")
                notifyItemChanged(adapterPosition, isFavorite)
            }
        }

        fun updateBookmarkIcon(isFavorite: Boolean) {
            binding.ivBookmark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_white_fill else R.drawable.ic_bookmark_white)
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnBookmarkListener(listener: OnQuantityClickListener) {
        bookmarkClickListener = listener
    }
}