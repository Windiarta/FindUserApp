package com.suitmediatest.injection

import com.suitmediatest.data.UserRepository
import com.suitmediatest.network.ApiConfig

object Injection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }
}