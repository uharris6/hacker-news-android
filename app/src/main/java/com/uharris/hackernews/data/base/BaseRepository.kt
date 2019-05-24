package com.uharris.hackernews.data.base

import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T: Any> safeApiCall(call: Deferred<Response<T>>, default: T): Result<T> {
        return try {
            val response = call.await()
            when (response.isSuccessful) {
                true -> Result.Success(response.body() ?: default)
                false -> Result.Success(default)
            }
        } catch (exception: Throwable) {
            Result.Error(Failure.ServerError)
        }
    }
}