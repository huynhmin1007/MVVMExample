package com.example.androidmvvm.data.api.app.service

import com.example.androidmvvm.data.api.app.Response
import com.example.androidmvvm.data.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService : ApiService {

    @POST("user/sign-in")
    suspend fun login(
        @Query("email") username: String,
        @Query("password") password: String
    ): Response<User>

    @GET("user/list")
    suspend fun findAll(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("orderDir") orderDir: String
    ): Response<List<User>>
}