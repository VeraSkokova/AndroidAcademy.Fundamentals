package ru.skokova.android_academy.data.mapper

import ru.skokova.android_academy.data.model.ImageLoadingInfo
import ru.skokova.android_academy.data.network.ConfigurationResponse

class JsonImageLoadingInfoMapper {
    fun toConfiguration(configurationResponse: ConfigurationResponse): ImageLoadingInfo {
        val baseImageUrl = configurationResponse.images.secureBaseUrl

        val backdropBaseUrl =
            addSizeToBaseUrl(baseImageUrl, configurationResponse.images.backdropSizes)
        val posterBaseUrl =
            addSizeToBaseUrl(baseImageUrl, configurationResponse.images.posterSizes)
        val profileBaseUrl =
            addSizeToBaseUrl(baseImageUrl, configurationResponse.images.profileSizes)
        return ImageLoadingInfo(backdropBaseUrl, posterBaseUrl, profileBaseUrl)
    }

    private fun addSizeToBaseUrl(baseUrl: String, sizes: List<String>): String {
        return baseUrl + sizes.last()
    }
}