package com.example.drevmassapp.domain.usecase

import android.util.Log
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.model.ProductResponse
import com.example.drevmassapp.domain.repository.CatalogRepository


class CatalogUseCase(private val catalogRepository: CatalogRepository) {

    suspend fun getProducts(int: Int): List<ProductResponse> {
        return catalogRepository.getProducts(int)
    }

    suspend fun getProductsById(id: Int): List<ProductByIdResponse> {
        return catalogRepository.getProductsById(id)
    }
}