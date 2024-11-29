package com.example.drevmassapp.presentation.course

import android.util.Log
import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.model.CourseBonusResponse
import com.example.drevmassapp.domain.model.CourseGetResponse
import com.example.drevmassapp.domain.usecase.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseUseCase: CourseUseCase
): ViewModel() {

    private var _course = MutableLiveData<CourseGetResponse>()
    val course: LiveData<CourseGetResponse> get() = _course

    private val _bonus = MutableLiveData<CourseBonusResponse>()
    val bonus: LiveData<CourseBonusResponse> get() = _bonus

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun getCourse(){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(false)
            try {
                val response = courseUseCase.getCourse()
                Log.d("CourseViewModel", "Response $response")
                _course.postValue(response)
                _error.postValue(null)
            }catch (e: Exception){
                Log.d("CourseViewModel","${e.message}")
                _error.postValue(e.message)
            }finally {
                _loading.postValue(true)
            }
        }
    }

    fun getBonus(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = courseUseCase.getCourseBonus()
            _bonus.postValue(response)
        }
    }

}