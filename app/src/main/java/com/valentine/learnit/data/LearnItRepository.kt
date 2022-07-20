package com.valentine.learnit.data

import androidx.paging.*
import com.valentine.learnit.internet.CoursesResponse
import com.valentine.learnit.main.NETWORK_PAGE_SIZE
import com.valentine.learnit.internet.Result
import com.valentine.learnit.internet.udemyapi.UdemyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class LearnItRepository(private val service: UdemyService) {

    fun getCourses(query: String, scope: CoroutineScope) : Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {LearnItPagingSource(service, query)}
        ) .flow.cachedIn(scope)
    }

    fun getSearchCourses(query: String?, scope: CoroutineScope) : Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {SearchPagingSource(service, query)}
        ).flow.cachedIn(scope)
    }

    suspend fun getCoursesByCategory(category: String): CoursesResponse {
        return service.getCoursesByCategory(category)
    }

}