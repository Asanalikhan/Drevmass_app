package com.example.drevmassapp.presentation.detailed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.usecase.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val useCase: CourseUseCase
): ViewModel() {

    private var _response = MutableLiveData<CourseByIdResponse>()
    val response: LiveData<CourseByIdResponse> get() = _response

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getCourse(course_id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(false)
            try {
                val response = useCase.getCourseById(course_id)
                Log.d("DetailedViewModel", "CourseById $response")
                _response.postValue(response)
                _error.postValue(null)
            }catch (e: Exception){
                _error.postValue(e.message)
            }finally {
                _loading.postValue(true)
            }
        }
    }
}