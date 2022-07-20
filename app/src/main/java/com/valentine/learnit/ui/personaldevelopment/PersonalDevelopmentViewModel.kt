package com.valentine.learnit.ui.personaldevelopment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class PersonalDevelopmentViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getPersonalDevCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Personal Development", viewModelScope)
    }
}

class PersonalDevelopmentFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalDevelopmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonalDevelopmentViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}