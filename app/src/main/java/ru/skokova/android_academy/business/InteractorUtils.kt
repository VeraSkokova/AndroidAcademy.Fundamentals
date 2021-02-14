package ru.skokova.android_academy.business

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object InteractorUtils {
    private val networkExceptions = setOf(
        UnknownHostException::class,
        SocketTimeoutException::class,
        ConnectException::class
    )

    fun isNetworkException(exception: Throwable) = networkExceptions.contains(exception::class)
}