package com.example.drevmassapp.presentation.basket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.model.BasketRequest
import com.example.drevmassapp.domain.usecase.CatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val catalogUseCase: CatalogUseCase
) : ViewModel() {

    private val _basket = MutableLiveData<BasketGetResponse>()
    val basket: LiveData<BasketGetResponse> get() = _basket

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _basketUpdated = MutableLiveData<Unit>()
    val basketUpdated: LiveData<Unit> get() = _basketUpdated

    fun getBasket(is_using: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                val response = catalogUseCase.getBasket(is_using)
                _basket.postValue(response)
                _error.postValue(null)
                Log.d("BasketViewModel", "Basket: $response")
            } catch (e: Exception) {
                _error.postValue(e.message)
                Log.d("BasketViewModel", "Error: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun addBasket(count: Int, productId: Int, userId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _basketUpdated.postValue(Unit)
            try {
                val request = BasketRequest(count, productId, userId)
                Log.d("BasketViewModel", "addBasket request: $request")
                catalogUseCase.addBasket(request)
            } catch (e: Exception) {
                Log.d("BasketViewModel", "Error: ${e.message}")
            }finally {
                _basketUpdated.postValue(Unit)
            }
        }
    }

    fun increaseBasket(count: Int, productId: Int, userId: Int, increase: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            _basketUpdated.postValue(Unit)
            try {
                val request = BasketRequest(count, productId, userId)
                val response = if (increase) {
                    catalogUseCase.increaseBasket(request)
                } else {
                    catalogUseCase.decreaseBasket(request)
                }
                Log.d("BasketViewModel", "Response: $response")
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Error: ${e.message}")
            }finally {
                _basketUpdated.postValue(Unit)
            }
        }
    }

    fun delete(){
        viewModelScope.launch(Dispatchers.IO) {
            _basketUpdated.postValue(Unit)
            try{
                val response = catalogUseCase.delete()
                Log.d("BasketViewModel", "delete: $response")
            }catch (e: Exception){
                Log.d("BasketViewModel", "${e.message}")
            }finally {
                _basketUpdated.postValue(Unit)
            }
        }
    }

    fun deleteById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _basketUpdated.postValue(Unit)
            try {
                val response = catalogUseCase.deleteById(id)
                Log.d("BasketViewModel", "deleteById: $response")
            }catch (e: Exception){
                Log.d("BasketViewModel", "${e.message}")
            }finally {
                _basketUpdated.postValue(Unit)
            }
        }
    }
}