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
        var response: List<ProductResponse> = emptyList()
        when(int){
            0 -> response = apiService.getFamousProducts(preferencesRepository.getUserToken())
            1 -> response = apiService.getPriceDownProducts(preferencesRepository.getUserToken())
            2 -> response = apiService.getPriceUpProducts(preferencesRepository.getUserToken())
        }
        Log.d("AuthRepository", "getProducts: $response")
        return response
    }

    override suspend fun getProductsById(id: Int): List<ProductByIdResponse> {
        val response = apiService.getProductById(id, preferencesRepository.getUserToken())
        Log.d("AuthRepository", "getProductsById: $response")
        return listOf(response)
    }
}