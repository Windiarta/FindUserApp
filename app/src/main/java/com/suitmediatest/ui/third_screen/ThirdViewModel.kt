package com.suitmediatest.ui.third_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.suitmediatest.data.User
import com.suitmediatest.data.UserRepository
import com.suitmediatest.injection.Injection

class ThirdViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users: LiveData<PagingData<User>> = userRepository.getUser().cachedIn(viewModelScope)
}

class ThirdViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThirdViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThirdViewModel(Injection.provideRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}