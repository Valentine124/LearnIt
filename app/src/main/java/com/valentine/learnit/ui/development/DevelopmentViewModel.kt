package com.valentine.learnit.ui.development

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class DevelopmentViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getDevelopmentCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Development", viewModelScope)
    }

}

class DevelopmentFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevelopmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DevelopmentViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}