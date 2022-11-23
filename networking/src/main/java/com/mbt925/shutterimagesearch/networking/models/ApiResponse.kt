package com.mbt925.shutterimagesearch.networking.models

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

sealed interface ApiResponse<out T> {

    data class Success<T>(val data: T?) : ApiResponse<T>

    sealed interface Failure : ApiResponse<Nothing> {

        data class Known(
            val statusCode: Int,
            val error: ApiError,
        ) : Failure

        data class Unknown(val error: Throwable) : Failure

    }

    companion object
}

fun <T> ApiResponse.Companion.success(data: T?) = ApiResponse.Success(data)

fun ApiResponse.Companion.failure(
    statusCode: Int,
    errorBody: String,
): ApiResponse<Nothing> = ApiResponse.Failure.Known(
    statusCode = statusCode,
    error = Json.decodeFromString(errorBody),
)

fun ApiResponse.Companion.failure(
    error: Throwable,
): ApiResponse<Nothing> = ApiResponse.Failure.Unknown(error)
