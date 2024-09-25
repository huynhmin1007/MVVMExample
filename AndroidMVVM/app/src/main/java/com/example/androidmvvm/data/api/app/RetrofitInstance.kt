package com.example.androidmvvm.data.api.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val IP = "10.52.50.50"
    private const val PORT = "8081"
    private const val API = "api.example.com"

    private const val BASE_URL = "http://$IP:$PORT/$API/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <ApiService> createService(serviceClass: Class<ApiService>): ApiService {
        return retrofit.create(serviceClass)
    }
}