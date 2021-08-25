package com.yougetme.app.api

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(string: String, data: T? = null) : Resource<T>(data, string)
    class Empty<T>() : Resource<T>()
}