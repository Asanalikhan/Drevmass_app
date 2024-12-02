package com.example.drevmassapp.presentation.detailed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemLessonBinding
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener

class LessonAdapter : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    private var products = mutableListOf<CourseByIdResponse.Course.Lesson>()
    private lateinit var itemClickListener: OnItemClickListener

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
        return holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(list: List<CourseByIdResponse.Course.Lesson>) {
        products.clear()
        products.addAll(list)
        notifyDataSetChanged()
    }

    inner class LessonViewHolder(val binding: ItemLessonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CourseByIdResponse.Course.Lesson) {
            Glide.with(binding.ivLesson.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivLesson)
            binding.minutes.text = product.duration.toString()
            binding.countLessons.text = product.orderId.toString()
            binding.tvTitle.text = product.title

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
            }

            binding.ivBookmark.setOnClickListener {
                TODO("add bookmark onclick feature")
            }

        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

}