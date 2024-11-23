package com.example.drevmassapp.presentation.basket

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.ItemBasketBinding
import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>(){

    private val products = mutableListOf<BasketGetResponse.Basket>()
    private lateinit var itemClickListener: OnQuantityClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        return holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(list: List<BasketGetResponse.Basket>) {
        products.clear()
        products.addAll(list)
        notifyDataSetChanged()
    }

    inner class BasketViewHolder(val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: BasketGetResponse.Basket) {
            Glide.with(binding.ivImage.context)
                .load(ServiceBuilder.getUrl() + product.productImg)
                .into(binding.ivImage)
            binding.tvPrice.text = "${product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = product.productTitle
            binding.tvQuantity.text = product.count.toString()

            binding.btnPlus.setOnClickListener {
                binding.tvQuantity.text = "${product.count}"
                itemClickListener.onQuantityChanged(product.count, product.productId, false)
            }

            binding.btnMinus.setOnClickListener {
                if (product.count > 0) {
                    binding.tvQuantity.text = "${product.count}"
                    itemClickListener.onQuantityChanged(product.count, product.productId, false)
                }
            }

        }
    }

    fun setOnQuantityClickListener(listener: OnQuantityClickListener) {
        itemClickListener = listener
    }

    private fun Int.formatWithSpaces(): String {
        return this.toString().reversed().chunked(3).joinToString(" ").reversed()
    }

}