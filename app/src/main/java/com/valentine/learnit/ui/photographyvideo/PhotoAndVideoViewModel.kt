package com.valentine.learnit.ui.photographyvideo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class PhotoAndVideoViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getPhotographyAndVideoCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Photography & Video", viewModelScope)
    }
}

class PhotoAndVideoFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoAndVideoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoAndVideoViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}