package com.valentine.learnit.ui.teachingacademics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.internet.Result
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.db.RecentCourses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TeachingAndAcademicsViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getTeachingAndAcademicsCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Teaching & Academics", viewModelScope)
    }
}

class TeachingAndAcademicsFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeachingAndAcademicsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeachingAndAcademicsViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}