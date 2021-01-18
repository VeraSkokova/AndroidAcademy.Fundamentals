package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.network.GenresResponse
import ru.skokova.android_academy.data.network.RetrofitModule

interface GenresRepository {
    suspend fun loadGenres(): GenresResponse
}

class GenresLoader : GenresRepository {
    override suspend fun loadGenres(): GenresResponse {
        return RetrofitModule.moviesApi.getGenres()
    }
}