package com.example.drevmassapp.data.repository

import android.util.Log
import android.content.Context
import com.example.drevmassapp.data.api.ApiService
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.remote.ServiceBuilder
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
            Log.d("CatalogRepositoryImpl", "Token: $token")
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
        Log.d("AuthRepository", "getProductsById: $response")
        return listOf(response)
    }
}