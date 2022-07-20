package com.valentine.learnit.ui.healthfitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class HealthAndFitnessViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getHealthAndFitnessCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Health & Fitness", viewModelScope)
    }
}

class HealthAndFitFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthAndFitnessViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HealthAndFitnessViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}