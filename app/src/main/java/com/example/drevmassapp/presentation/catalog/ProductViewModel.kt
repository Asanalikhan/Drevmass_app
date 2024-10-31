package com.example.drevmassapp.presentation.catalog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.usecase.AuthUseCase
import com.example.drevmassapp.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val catalogUseCase: CatalogUseCase
): ViewModel() {

    private val _productById = MutableLiveData<List<ProductByIdResponse>>()
    val productById: LiveData<List<ProductByIdResponse>> get() = _productById

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getProductsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                val response = catalogUseCase.getProductsById(id)
                _productById.postValue(response)
                _error.postValue(null)
                Log.d("CatalogViewModel", "ProductById: $response")
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