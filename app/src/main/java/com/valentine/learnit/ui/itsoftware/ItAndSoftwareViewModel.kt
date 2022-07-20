package com.valentine.learnit.ui.itsoftware

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class ItAndSoftwareViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getItAndSoftwareCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("IT & Software", viewModelScope)
    }
}

class ItAndSoftwareFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItAndSoftwareViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItAndSoftwareViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}