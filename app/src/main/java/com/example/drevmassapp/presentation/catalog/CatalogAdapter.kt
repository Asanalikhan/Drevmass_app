package com.example.drevmassapp.presentation.catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemCatalogGridBinding
import com.example.drevmassapp.databinding.ItemCatalogHorizontalBinding
import com.example.drevmassapp.databinding.ItemCatalogVerticalBinding
import com.example.drevmassapp.domain.model.ProductResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener

class CatalogAdapter(private val layoutType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val GRID_LAYOUT = 0
        const val HORIZONTAL_LAYOUT = 1
        const val VERTICAL_LAYOUT = 2
        private lateinit var itemClickListener: OnItemClickListener
        private lateinit var itemQuantityClickListener: OnQuantityClickListener
    }

    private val products = mutableListOf<ProductResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (layoutType) {
            GRID_LAYOUT -> {
                val binding = ItemCatalogGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GridViewHolder(binding)
            }

            HORIZONTAL_LAYOUT -> {
                val binding = ItemCatalogHorizontalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HorizontalViewHolder(binding)
            }

            else -> {
                val binding = ItemCatalogVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val isAddedToCart = payloads[0] as Boolean
            when (holder) {
                is GridViewHolder -> {
                    holder.binding.ibAddToCart.setImageResource(
                        if (isAddedToCart) R.drawable.ic_cart_added else R.drawable.ic_cart_not_added
                    )
                }

                is HorizontalViewHolder -> {
                    holder.binding.ibAddToCart.setImageResource(
                        if (isAddedToCart) R.drawable.ic_cart_added else R.drawable.ic_cart_not_added
                    )
                }

                is VerticalViewHolder -> {
                    holder.binding.ibAddToCart.setImageResource(
                        if (isAddedToCart) R.drawable.ic_cart_added else R.drawable.ic_cart_not_added
                    )
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
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

    inner class GridViewHolder(val binding: ItemCatalogGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var notSelected: Boolean = false
        fun bind(product: ProductResponse) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.title

            binding.ibAddToCart.setImageResource(
                if (product.basketCount != 0) R.drawable.ic_cart_added else R.drawable.ic_cart_not_added
            )

            notSelected = product.basketCount == 0
            binding.ibAddToCart.setOnClickListener {
                if (notSelected) itemQuantityClickListener.onQuantityChanged(
                    1,
                    product.id,
                    true
                ) else itemQuantityClickListener.onQuantityChanged(1, product.id, false)
                notifyItemChanged(adapterPosition, product.basketCount == 0)
            }


            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
                Log.d("CatalogAdapter", "onItemClick: ${product.id}")
            }
        }
    }

    inner class HorizontalViewHolder(val binding: ItemCatalogHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var notSelected: Boolean = false
        fun bind(product: ProductResponse) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.title

            binding.ibAddToCart.setImageResource(
                if (product.basketCount != 0) R.drawable.ic_cart_added else R.drawable.ic_cart_not_added
            )

            notSelected = product.basketCount == 0
            binding.ibAddToCart.setOnClickListener {
                if (notSelected) itemQuantityClickListener.onQuantityChanged(
                    1,
                    product.id,
                    true
                ) else itemQuantityClickListener.onQuantityChanged(1, product.id, false)
                notifyItemChanged(adapterPosition, product.basketCount == 0)
            }

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
                Log.d("CatalogAdapter", "onItemClick: ${product.id}")
            }
        }
    }

    inner class VerticalViewHolder(val binding: ItemCatalogVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var notSelected: Boolean = false
        fun bind(product: ProductResponse) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.imageSrc)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.title

            binding.ibAddToCart.setImageResource(
                if (product.basketCount != 0) R.drawable.ic_cart_added else R.drawable.ic_cart_not_added
            )

            notSelected = product.basketCount == 0
            binding.ibAddToCart.setOnClickListener {
                if (notSelected) itemQuantityClickListener.onQuantityChanged(
                    1,
                    product.id,
                    true
                ) else itemQuantityClickListener.onQuantityChanged(1, product.id, false)
                notifyItemChanged(adapterPosition, product.basketCount == 0)
            }

            binding.root.setOnClickListener {
                itemClickListener.onItemClick(product.id)
                Log.d("CatalogAdapter", "onItemClick: ${product.id}")
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnItemQuantityClickListener(listener: OnQuantityClickListener) {
        itemQuantityClickListener = listener
    }

    private fun Int.formatWithSpaces(): String {
        return this.toString().reversed().chunked(3).joinToString(" ").reversed()
    }
}