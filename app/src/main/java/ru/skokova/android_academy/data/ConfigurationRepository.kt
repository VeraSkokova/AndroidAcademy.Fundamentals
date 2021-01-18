package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.network.ConfigurationResponse
import ru.skokova.android_academy.data.network.RetrofitModule

interface ConfigurationRepository {
    suspend fun getConfiguration(): ConfigurationResponse
}

class ConfigurationLoader : ConfigurationRepository {
    override suspend fun getConfiguration(): ConfigurationResponse {
        return RetrofitModule.moviesApi.getConfiguration()
    }
}