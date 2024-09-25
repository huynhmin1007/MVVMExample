package com.example.androidmvvm.ui.data

sealed class Resource<out T> {
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val exception: Throwable): Resource<Nothing>()
    object Loading : Resource<Nothing>()
}