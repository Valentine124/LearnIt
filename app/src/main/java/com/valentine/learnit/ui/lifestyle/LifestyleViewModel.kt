package com.valentine.learnit.ui.lifestyle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class LifestyleViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getLifestyleCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Lifestyle", viewModelScope)
    }
}

class LifestyleFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LifestyleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LifestyleViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}