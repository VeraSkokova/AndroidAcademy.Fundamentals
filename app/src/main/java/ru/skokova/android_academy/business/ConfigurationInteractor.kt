package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.ConfigurationRepository
import ru.skokova.android_academy.data.TmdbConfiguration
import ru.skokova.android_academy.data.converter.ConfigurationMapper

class ConfigurationInteractor(private val configurationRepository: ConfigurationRepository) {
    suspend fun getConfiguration(): TmdbConfiguration = withContext(Dispatchers.IO) {
        val configuration = configurationRepository.getConfiguration()
        val configurationConverter = ConfigurationMapper()
        configurationConverter.toConfiguration(configuration)
    }
}