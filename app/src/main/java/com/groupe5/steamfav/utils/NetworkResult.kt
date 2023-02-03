package com.groupe5.steamfav.utils

sealed class NetworkResult<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }


    data class Success<out T>(val _data: T?) : NetworkResult<T>(Status.SUCCESS, data=_data, null)


    data class Error<out T>(val msg: String, val _data: T?=null) : NetworkResult<T>(Status.ERROR, data=_data, msg)


    data class Loading<out T>(val _data: T? = null) : NetworkResult<T>(Status.LOADING, data=_data, null)
}