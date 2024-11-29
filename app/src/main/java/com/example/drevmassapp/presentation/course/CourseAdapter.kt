package com.example.drevmassapp.presentation.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemCourseBinding
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener

class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var products = mutableListOf<CourseGetResponse.CourseGetResponseItem>()
    private lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            ItemCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        return holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(list: List<CourseGetResponse.CourseGetResponseItem>) {
        products.clear()
        products.addAll(list)
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CourseGetResponse.CourseGetResponseItem) {
            Glide.with(binding.ivCourse.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivCourse)
            binding.minutes.text = product.duration.toString()
            if(product.bonusInfo.price != 0) binding.tvBonus.text = product.bonusInfo.price.toString()
            else binding.tvBonus.visibility = View.GONE
            binding.countLessons.text = product.lessonCnt.toString()
            binding.tvTitle.text = product.name
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
            }
            if(product.lessonCnt >= 4){
                binding.tvLessons.text = "уроков"
            }else if(product.lessonCnt < 4){
                binding.tvLessons.text = "урока"
            }
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

}