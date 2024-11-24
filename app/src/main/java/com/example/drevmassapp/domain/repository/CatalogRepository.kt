package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.model.BasketRequest
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.model.ProductResponse

interface CatalogRepository {
    suspend fun getProducts(int: Int): List<ProductResponse>
    suspend fun getProductsById(id: Int): List<ProductByIdResponse>
    suspend fun getBasket(is_using: String): BasketGetResponse
    suspend fun addBasket(basket: BasketRequest): ForgotModel
    suspend fun decreaseBasket(basket: BasketRequest): ForgotModel
    suspend fun increaseBasket(basket: BasketRequest): ForgotModel
    suspend fun delete(): ForgotModel
    suspend fun deleteById(id: Int): ForgotModel
}