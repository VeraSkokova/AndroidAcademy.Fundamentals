package ru.skokova.android_academy.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.skokova.android_academy.MoviesApplication
import ru.skokova.android_academy.R

class TmdbApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(
                API_KEY_HEADER,
                MoviesApplication.context.getString(R.string.tmdb_api_key)
            )
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        const val API_KEY_HEADER = "api_key"
    }
}