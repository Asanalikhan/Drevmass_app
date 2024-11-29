package com.example.drevmassapp.presentation.lesson

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.LessonByIdResponse
import com.example.drevmassapp.domain.usecase.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val useCase: CourseUseCase
): ViewModel() {

    private val _lesson = MutableLiveData<LessonByIdResponse>()
    val lesson: LiveData<LessonByIdResponse> get() = _lesson

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getLesson(id: Int, course_id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(false)
            try {
                val response = useCase.getLessonById(id, course_id)
                Log.d("LessonViewModel", "Response $response")
                _lesson.postValue(response)
                _error.postValue(null)
            }catch (e: Exception){
                _error.postValue(e.message)
            }finally {
                _loading.postValue(true)
            }
        }
    }
}