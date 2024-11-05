package com.example.drevmassapp.presentation.catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemCatalogGridBinding
import com.example.drevmassapp.databinding.ItemCatalogHorizontalBinding
import com.example.drevmassapp.databinding.ItemCatalogVerticalBinding
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.model.ProductResponse

class CatalogAdapter(private val layoutType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val GRID_LAYOUT = 0
        const val HORIZONTAL_LAYOUT = 1
        const val VERTICAL_LAYOUT = 2
        private lateinit var itemClickListener: OnItemClickListener
    }

    private val products = mutableListOf<ProductResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (layoutType) {
            GRID_LAYOUT -> {
                val binding = ItemCatalogGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridViewHolder(binding)
            }
            HORIZONTAL_LAYOUT -> {
                val binding = ItemCatalogHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HorizontalViewHolder(binding)
            }
            else -> {
                val binding = ItemCatalogVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        when (holder) {
            is GridViewHolder -> holder.bind(product)
            is HorizontalViewHolder -> holder.bind(product)
            is VerticalViewHolder -> holder.bind(product)
        }
    }

    override fun getItemCount(): Int = products.size

    fun getProducts(): List<ProductResponse> {
        return products
    }

    fun setProducts(list: List<ProductResponse>) {
        products.clear()
        products.addAll(list)
        notifyDataSetChanged()
    }

    inner class GridViewHolder(private val binding: ItemCatalogGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponse) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.title
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
                Log.d("CatalogAdapter", "onItemClick: ${product.id}")
            }
        }
    }

    inner class HorizontalViewHolder(private val binding: ItemCatalogHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponse) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.title
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
                Log.d("CatalogAdapter", "onItemClick: ${product.id}")
            }
        }
    }

    inner class VerticalViewHolder(private val binding: ItemCatalogVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponse) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.title
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
                Log.d("CatalogAdapter", "onItemClick: ${product.id}")
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    private fun Int.formatWithSpaces(): String {
        return this.toString().reversed().chunked(3).joinToString(" ").reversed()
    }
}