package com.example.drevmassapp.data.repository

import android.util.Log
import android.content.Context
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.model.BasketRequest
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.model.ProductResponse
import com.example.drevmassapp.domain.repository.CatalogRepository

class CatalogRepositoryImpl(private val context: Context): CatalogRepository {

    private val apiService = ServiceBuilder.buildService(ApiService::class.java)
    private val preferencesRepository = PreferencesRepositoryImpl(PreferencesManager(context))

    override suspend fun getProducts(int: Int): List<ProductResponse> {
        Log.d("CatalogRepositoryImpl", "getProducts called with parameter: $int")
        var response: List<ProductResponse> = emptyList()
        try {
            val token = preferencesRepository.getUserToken()
            response = when(int) {
                1 -> apiService.getFamousProducts("Bearer $token")
                2 -> apiService.getPriceDownProducts("Bearer $token")
                3 -> apiService.getPriceUpProducts("Bearer $token")
                else -> emptyList()
            }
            Log.d("CatalogRepositoryImpl", "API Response: $response")
        } catch (e: Exception) {
            Log.e("CatalogRepositoryImpl", "Error fetching products", e)
        }
        return response
    }

    override suspend fun getProductsById(id: Int): List<ProductByIdResponse> {
        val response = apiService.getProductById(id, "Bearer ${preferencesRepository.getUserToken()}")
        Log.d("CatalogRepository", "getProductsById: $response")
        return listOf(response)
    }

    override suspend fun getBasket(is_using: String): BasketGetResponse {
        val response = apiService.getBasket(is_using, "Bearer ${preferencesRepository.getUserToken()}")
        Log.d("CatalogRepository", "getBasket: $response")
        return response
    }

    override suspend fun addBasket(basket: BasketRequest): ForgotModel {
        val response = apiService.addBasket("Bearer ${preferencesRepository.getUserToken()}", basket)
        Log.d("CatalogRepository", "addBasket: $response")
        return response
    }

    override suspend fun decreaseBasket(basket: BasketRequest): ForgotModel {
        val response = apiService.decreaseBasket("Bearer ${preferencesRepository.getUserToken()}", basket)
        Log.d("CatalogRepository", "decreaseBasket: $response")
        return response
    }

    override suspend fun increaseBasket(basket: BasketRequest): ForgotModel {
        val response = apiService.increaseBasket("Bearer ${preferencesRepository.getUserToken()}", basket)
        Log.d("CatalogRepository", "increaseBasket: $response")
        return response
    }

    override suspend fun delete(): ForgotModel{
        val response = apiService.delete("Bearer ${preferencesRepository.getUserToken()}")
        Log.d("CatalogRepository", "delete: $response")
        return response
    }

    override suspend fun deleteById(id: Int): ForgotModel{
        val response = apiService.deleteById(id, "Bearer ${preferencesRepository.getUserToken()}")
        Log.d("CatalogRepository", "delegeByid: ${response}")
        return response
    }
}