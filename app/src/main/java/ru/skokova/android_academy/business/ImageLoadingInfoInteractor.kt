package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.ImageLoadingInfoRepository
import ru.skokova.android_academy.data.model.ImageLoadingInfo

class ImageLoadingInfoInteractor(
    private val networkRepository: ImageLoadingInfoRepository,
    private val cachedRepository: ImageLoadingInfoRepository
) {
    suspend fun getImageLoadingInfo(): ImageLoadingInfo = withContext(Dispatchers.IO) {
        cachedRepository.getImageLoadingInfo() ?: run {
            networkRepository.getImageLoadingInfo() ?: throw IllegalStateException()
        }
    }
}