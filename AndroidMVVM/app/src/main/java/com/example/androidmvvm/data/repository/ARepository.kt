package com.example.androidmvvm.data.repository

import com.example.androidmvvm.data.api.app.Response
import com.example.androidmvvm.ui.data.Resource
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class ARepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccess) {
                    Resource.Success(response.data)
                } else {
                    Resource.Failure(Exception(response.message))
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = parseError(errorBody)

                Resource.Failure(Exception(errorResponse?.message ?: "Unknown error"))
            } catch (e: IOException) {
                Resource.Failure(Exception("Network error: ${e.message}"))
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }

    private fun parseError(errorBody: String?): Response<*>? {
        return try {
            errorBody?.let {
                Gson().fromJson(it, Response::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }
}