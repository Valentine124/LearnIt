package com.valentine.learnit.ui.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class BusinessViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getBusinessCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Business" ,viewModelScope)
    }

}

class BusinessFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}