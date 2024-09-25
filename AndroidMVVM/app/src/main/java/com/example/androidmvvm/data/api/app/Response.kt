package com.example.androidmvvm.data.api.app

data class Response<out T>(
    val code: Int,
    val message: String,
    val data: T
) {
    val isSuccess: Boolean
        get() = data != null && code in 200..299
}