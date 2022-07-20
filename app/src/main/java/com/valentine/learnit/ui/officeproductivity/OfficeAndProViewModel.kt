package com.valentine.learnit.ui.officeproductivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class OfficeAndProViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getOfficeProCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Office Productivity", viewModelScope)
    }
}

class OfficeProFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfficeAndProViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OfficeAndProViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}