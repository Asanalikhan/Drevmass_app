package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.model.ProductResponse

interface CatalogRepository {
    suspend fun getProducts(int: Int): List<ProductResponse>
    suspend fun getProductsById(id: Int): List<ProductByIdResponse>
}