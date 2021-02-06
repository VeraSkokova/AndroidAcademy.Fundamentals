package ru.skokova.android_academy.viewmodel

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ViewModelUtils {
    private val networkExceptions = setOf(
        UnknownHostException::class,
        SocketTimeoutException::class,
        ConnectException::class
    )

    fun isNetworkException(exception: Throwable) = networkExceptions.contains(exception::class)
}