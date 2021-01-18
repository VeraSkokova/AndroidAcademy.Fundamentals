package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.network.ActorsResponse
import ru.skokova.android_academy.data.network.RetrofitModule

interface ActorsRepository {
    suspend fun getActors(movieId: Int): ActorsResponse
}

class ActorsLoader : ActorsRepository {
    override suspend fun getActors(movieId: Int): ActorsResponse {
        return RetrofitModule.moviesApi.getActors(movieId)
    }
}