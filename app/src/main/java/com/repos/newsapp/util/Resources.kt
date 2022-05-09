package com.repos.newsapp.util

sealed class Resources<T>(
    data: T? = null,
    message: String? = null
) {
    class Success<T>(data: T?) : Resources<T>(data)
    class Error<T>(data: T? = null, message: String?) : Resources<T>(data, message)
    class Loading<T>(isLoading: Boolean = false) : Resources<T>()
}
