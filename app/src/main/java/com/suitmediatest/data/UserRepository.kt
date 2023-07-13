package com.suitmediatest.data


import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.suitmediatest.network.ApiService

class UserRepository(private val apiService: ApiService) {
    fun getUser(): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 7, initialLoadSize = 7),
            pagingSourceFactory = { PagingSource(apiService) }
        ).liveData
    }

    companion object {
        private const val TAG = "UserRepository"
    }
}