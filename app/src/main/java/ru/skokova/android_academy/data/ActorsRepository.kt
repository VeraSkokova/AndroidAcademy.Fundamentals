package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.JsonActorsMapper
import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.network.MoviesApi

interface ActorsRepository {
    suspend fun getActors(movieId: Int): List<Actor>
}

class NetworkActorsRepository(
    private val moviesApi: MoviesApi,
    private val mapper: JsonActorsMapper
) : ActorsRepository {
    override suspend fun getActors(movieId: Int): List<Actor> {
        val actorsResponse = moviesApi.getActors(movieId)
        return mapper.toActors(actorsResponse)
    }
}