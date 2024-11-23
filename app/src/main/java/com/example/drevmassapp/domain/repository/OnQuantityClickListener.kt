package com.example.drevmassapp.domain.repository

interface OnQuantityClickListener {
    fun onQuantityChanged(productId: Int, newQuantity: Int, increase: Boolean)
}