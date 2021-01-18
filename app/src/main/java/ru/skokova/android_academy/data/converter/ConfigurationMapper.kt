package ru.skokova.android_academy.data.converter

import ru.skokova.android_academy.data.TmdbConfiguration
import ru.skokova.android_academy.data.network.ConfigurationResponse

class ConfigurationMapper {
    fun toConfiguration(configurationResponse: ConfigurationResponse): TmdbConfiguration {
        val baseImageUrl = configurationResponse.images.secureBaseUrl

        val backdropBaseUrl =
            addSizeToBaseUrl(baseImageUrl, configurationResponse.images.backdropSizes)
        val posterBaseUrl =
            addSizeToBaseUrl(baseImageUrl, configurationResponse.images.posterSizes)
        val profileBaseUrl =
            addSizeToBaseUrl(baseImageUrl, configurationResponse.images.profileSizes)
        return TmdbConfiguration(backdropBaseUrl, posterBaseUrl, profileBaseUrl)
    }

    private fun addSizeToBaseUrl(baseUrl: String, sizes: List<String>): String {
        return baseUrl + sizes[sizes.size - 1]
    }
}