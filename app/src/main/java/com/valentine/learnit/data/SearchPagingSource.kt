package com.valentine.learnit.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.valentine.learnit.main.NETWORK_PAGE_SIZE
import com.valentine.learnit.main.UDEMY_STARTING_PAGE_INDEX
import com.valentine.learnit.internet.Result
import com.valentine.learnit.internet.udemyapi.UdemyService

class SearchPagingSource
    (private val service: UdemyService, private val query: String?)
    : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: UDEMY_STARTING_PAGE_INDEX

        return try {
            val response = service.getSearchedCourses(query, position)
            val result = response.results
            val nextKey = if (result.isEmpty()) {
                null
            } else{
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = result,
                prevKey = if (position == UDEMY_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}