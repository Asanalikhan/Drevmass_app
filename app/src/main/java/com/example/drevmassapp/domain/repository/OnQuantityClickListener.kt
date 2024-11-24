package com.example.drevmassapp.domain.repository

interface OnQuantityClickListener {
    fun onQuantityChanged(newQuantity: Int, productId: Int, increase: Boolean)
}