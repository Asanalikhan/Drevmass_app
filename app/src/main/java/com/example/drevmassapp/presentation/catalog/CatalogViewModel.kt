package com.example.drevmassapp.presentation.catalog

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.model.ProductResponse
import com.example.drevmassapp.domain.usecase.AuthUseCase
import com.example.drevmassapp.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogUseCase: CatalogUseCase
) : ViewModel() {

    private val _products = MutableLiveData<List<ProductResponse>>()
    val products: LiveData<List<ProductResponse>> get() = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getProducts(int: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                val response = catalogUseCase.getProducts(int)
                _products.postValue(response)
                _error.postValue(null)
                Log.d("CatalogViewModel", "Products: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
                Log.d("CatalogViewModel", "Error: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

}