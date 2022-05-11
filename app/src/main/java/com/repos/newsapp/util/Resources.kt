package com.repos.newsapp.util

sealed class Resources<T>(
    data: T? = null,
    message: String? = null
) {
    data class Success<T>(val data: T?) : Resources<T>(data)
    data class Error<T>(val data: T? = null, val message: String?) : Resources<T>(data, message)
    data class Loading<T>(val isLoading: Boolean = false) : Resources<T>()
}
