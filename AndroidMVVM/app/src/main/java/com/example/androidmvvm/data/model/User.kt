package com.example.androidmvvm.data.model

import java.io.Serializable

data class User(
    val id: Int,
    val email: String,
    val name: String
) : Serializable