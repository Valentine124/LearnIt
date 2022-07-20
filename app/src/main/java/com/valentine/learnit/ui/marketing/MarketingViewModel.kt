package com.valentine.learnit.ui.marketing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class MarketingViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getMarketingCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Marketing", viewModelScope)
    }
}

class MarketingFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarketingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarketingViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}