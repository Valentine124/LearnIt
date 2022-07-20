package com.valentine.learnit.ui.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class MusicViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getMusicCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Music", viewModelScope)
    }
}

class MusicFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}