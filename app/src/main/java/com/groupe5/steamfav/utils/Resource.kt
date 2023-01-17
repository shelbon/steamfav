package com.groupe5.steamfav.utils

sealed class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }


    data class Success<out T>(val _data: T?) : Resource<T>(Status.SUCCESS, data=_data, null)


    data class Error<out T>(val msg: String, val _data: T?) : Resource<T>(Status.ERROR, data=_data, msg)


    data class Loading<out T>(val _data: T? = null) : Resource<T>(Status.LOADING, data=_data, null)
}