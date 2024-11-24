package com.example.drevmassapp.domain.usecase

import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.model.BasketRequest
import com.example.drevmassapp.domain.model.ForgotModel
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

    suspend fun getBasket(is_using: String): BasketGetResponse{
        return catalogRepository.getBasket(is_using)
    }

    suspend fun addBasket(basketRequest: BasketRequest): ForgotModel{
        return catalogRepository.addBasket(basketRequest)
    }

    suspend fun decreaseBasket(basketRequest: BasketRequest): ForgotModel{
        return catalogRepository.decreaseBasket(basketRequest)
    }

    suspend fun increaseBasket(basketRequest: BasketRequest): ForgotModel{
        return catalogRepository.increaseBasket(basketRequest)
    }

    suspend fun delete(): ForgotModel{
        return catalogRepository.delete()
    }

    suspend fun deleteById(id: Int): ForgotModel{
        return catalogRepository.deleteById(id)
    }
}