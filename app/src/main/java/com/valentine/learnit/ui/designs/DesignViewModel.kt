package com.valentine.learnit.ui.designs


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class DesignViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getDesignCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Design", viewModelScope)
    }
}

class DesignModelFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DesignViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DesignViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}
