package com.valentine.learnit.ui.financeaccounting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.valentine.learnit.data.LearnItRepository
import com.valentine.learnit.internet.Result
import kotlinx.coroutines.flow.Flow

class FinanceAndAccViewModel(private val repository: LearnItRepository): ViewModel() {

    fun getFinanceAndAccountingCourses() : Flow<PagingData<Result>> {
        return repository.getCourses("Finance & Accounting", viewModelScope)
    }
}

class FinanceAndAccFactory(private val repository: LearnItRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanceAndAccViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinanceAndAccViewModel(repository) as T
        }

        throw IllegalArgumentException("Class not found")
    }
}