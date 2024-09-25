package com.example.androidmvvm.data.repository

import com.example.androidmvvm.data.api.app.service.UserApiService
import com.example.androidmvvm.data.model.User
import com.example.androidmvvm.ui.data.Resource

class UserRepository(private val userApiService: UserApiService) : ARepository() {

    suspend fun login(username: String, password: String): Resource<User> {
        return safeApiCall(
            apiCall = { userApiService.login(username, password) }
        )
    }

    suspend fun findAll(page: Int, limit: Int, orderBy: String, orderDir: String
    ): Resource<List<User>> {
        return safeApiCall(
            apiCall = { userApiService.findAll(page, limit, orderBy, orderDir) }
        )
    }
}
