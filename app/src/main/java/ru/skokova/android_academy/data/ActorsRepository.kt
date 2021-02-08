package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.converter.ActorsMapper
import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.network.RetrofitModule

interface ActorsRepository {
    suspend fun getActors(movieId: Int): List<Actor>
}

class NetworkActorsRepository(private val mapper: ActorsMapper) : ActorsRepository {
    override suspend fun getActors(movieId: Int): List<Actor> {
        val actorsResponse = RetrofitModule.moviesApi.getActors(movieId)
        return mapper.toActors(actorsResponse)
    }
}