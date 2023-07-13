package com.suitmediatest.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.suitmediatest.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class PagingSource(private val apiService: ApiService) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = withContext(Dispatchers.IO) {
                apiService.getUsers(page = page, pageSize = params.loadSize).execute()
            }
            LoadResult.Page(
                data = response.body()?.data ?: emptyList(),
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (response.body()?.data.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.toString())
            if (exception is SocketTimeoutException) {
                delay(5000)
                return load(params)
            }
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        private const val TAG = "PagingSource"
        const val INITIAL_PAGE_INDEX = 1
    }
}