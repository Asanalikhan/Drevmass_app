package com.example.drevmassapp.presentation.bookmark

import android.content.om.FabricatedOverlay
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.FavoriteGetResponse
import com.example.drevmassapp.domain.model.FavoriteResponse
import com.example.drevmassapp.domain.model.ForgotModel
import com.example.drevmassapp.domain.usecase.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val useCase: CourseUseCase
): ViewModel() {

    private var _favorites = MutableLiveData<List<FavoriteResponse>>()
    val favorites: LiveData<List<FavoriteResponse>> get() = _favorites

    private var _response = MutableLiveData<ForgotModel>()
    val response: LiveData<ForgotModel> get() = _response

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getFavorites(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(false)
            try {
                val response = useCase.getFavorite()
                Log.d("BookmarkViewModel", response.toString())
                _favorites.postValue(response)
                _error.postValue(null)
            }catch (e: Exception){
                _error.postValue(e.message)
            }finally {
                _loading.postValue(true)
            }
        }
    }

    fun postFavorite(lesson_id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.postFavorite(lesson_id)
                Log.d("BookmarkViewModel", "postFavorite: $response")
                _response.postValue(response)
                _error.postValue(null)
            }catch (e: Exception){
                Log.e("BookmarkViewModel", "Error posting favorite", e)
                _error.postValue(e.message)
            }
        }
    }

    fun deleteFavorite(lesson_id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = useCase.deleteFavorite(lesson_id)
                Log.d("BookmarkViewModel", "deleteFavorite: $response")
                _response.postValue(response)
                _error.postValue(null)
            }catch (e: Exception){
                Log.e("BookmarkViewModel", "Error deleting favorite", e)
                _error.postValue(e.message)
            }
        }
    }

}