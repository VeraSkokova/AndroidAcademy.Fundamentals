package ru.skokova.android_academy.data

import android.content.Context
import ru.skokova.android_academy.MoviesApplication
import ru.skokova.android_academy.data.mapper.JsonImageLoadingInfoMapper
import ru.skokova.android_academy.data.model.ImageLoadingInfo
import ru.skokova.android_academy.data.network.MoviesApi

interface ImageLoadingInfoRepository {
    suspend fun getImageLoadingInfo(): ImageLoadingInfo?
}

class NetworkImageLoadingInfoRepository(
    private val moviesApi: MoviesApi,
    private val mapper: JsonImageLoadingInfoMapper
) : ImageLoadingInfoRepository {

    override suspend fun getImageLoadingInfo(): ImageLoadingInfo {
        val configurationResponse = moviesApi.getConfiguration()
        val imageLoadingInfo = mapper.toConfiguration(configurationResponse)
        saveImageLoadingInfo(imageLoadingInfo)
        return imageLoadingInfo
    }

    private fun saveImageLoadingInfo(imageLoadingInfo: ImageLoadingInfo) {
        val sharedPref =
            MoviesApplication.context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(URLS_SAVED, true)
            putString(BACKDROP_IMAGE_URL, imageLoadingInfo.baseBackdropImageUrl)
            putString(POSTER_IMAGE_URL, imageLoadingInfo.basePosterImageUrl)
            putString(PROFILE_IMAGE_URL, imageLoadingInfo.baseProfileImageUrl)
            commit()
        }
    }
}

class CachedImageLoadingInfoRepository() : ImageLoadingInfoRepository {

    override suspend fun getImageLoadingInfo(): ImageLoadingInfo? {
        if (!MoviesApplication.context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(
                    URLS_SAVED, false
                )
        ) {
            return null
        }

        val posterImageUrl =
            MoviesApplication.context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                .getString(POSTER_IMAGE_URL, null)
        val backdropImageUrl =
            MoviesApplication.context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                .getString(BACKDROP_IMAGE_URL, null)
        val profileImageUrl =
            MoviesApplication.context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                .getString(PROFILE_IMAGE_URL, null)
        return ImageLoadingInfo(backdropImageUrl, posterImageUrl, profileImageUrl)
    }
}

private const val SHARED_PREFS_NAME = "ru.skokova.android_academy.shared_prefs"
private const val BACKDROP_IMAGE_URL = "backdrop_image_url"
private const val POSTER_IMAGE_URL = "poster_image_url"
private const val PROFILE_IMAGE_URL = "profile_image_url"
private const val URLS_SAVED = "urls_saved"