package com.clip.domain.model

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(
        val code: Int = 0,
        val message: String? = null,
        val throwable: Throwable? = null,
    ) : NetworkResult<Nothing>()
}