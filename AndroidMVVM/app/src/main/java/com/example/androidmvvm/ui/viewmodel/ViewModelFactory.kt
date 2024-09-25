package com.example.androidmvvm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvm.data.api.app.service.ApiService
import com.example.androidmvvm.data.api.app.service.UserApiService
import com.example.androidmvvm.data.repository.UserRepository

class ViewModelFactory(
    private val apiService: ApiService
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(
                    userRepository = UserRepository(apiService as UserApiService)
                ) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(
                    userRepository = UserRepository(apiService as UserApiService)
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}