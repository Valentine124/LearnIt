package com.valentine.learnit.ui.explore

import android.util.Log
import androidx.lifecycle.*
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.data.NetworkState
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.launch

private const val TAG = "ExploreViewModel"
class ExploreViewModel(private val repository: LearnItRepository): ViewModel() {

    private var _developmentCourses = MutableLiveData<List<Result>>()
    val developmentCourses: LiveData<List<Result>> = _developmentCourses

    private var _designCourses = MutableLiveData<List<Result>>()
    val designCourses: LiveData<List<Result>> = _designCourses

    private var _itAndSoftwareCourses = MutableLiveData<List<Result>>()
    val itAndSoftwareCourses: LiveData<List<Result>> = _itAndSoftwareCourses

    private val _networkState = MutableLiveData<NetworkState>()
    var networkState: LiveData<NetworkState> = _networkState

    init {
        getAllCourses()
    }

    fun getAllCourses() {
        getDevelopmentCourses()
        getDesignCourses()
        getItAndSoftwareCourses()
    }

    private fun getItAndSoftwareCourses() {
        _networkState.value = NetworkState.LOADING
        viewModelScope.launch {
            try {

                val result = repository.getCoursesByCategory("IT & Software").results
                _itAndSoftwareCourses.value = result

                _networkState.value = NetworkState.SUCCESS

            }catch (e: Exception) {
                Log.e(TAG, e.message!!)
                _networkState.value = NetworkState.ERROR
            }
        }
    }

    private fun getDesignCourses() {
        viewModelScope.launch {
            try {

                val result = repository.getCoursesByCategory("Design").results
                _designCourses.value = result

            }catch (e: Exception) {
                Log.e(TAG, e.message!!)
            }
        }
    }

    private fun getDevelopmentCourses() {
        viewModelScope.launch {
            try {

                val result = repository.getCoursesByCategory("Development").results
                _developmentCourses.value = result

            }catch (e: Exception) {
                Log.e(TAG, e.message!!)
            }
        }
    }
}

class ExploreFactory(private val repository: LearnItRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExploreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}
