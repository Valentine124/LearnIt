package com.valentine.learnit.ui.search

import androidx.lifecycle.*
import com.valentine.learnit.data.LearnItRepository

class SearchViewModel(private val repository: LearnItRepository) : ViewModel(){

    fun getSearchCourses(query: String?) = repository.getSearchCourses(query, viewModelScope)


}
class SearchFactory(private val repository: LearnItRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}