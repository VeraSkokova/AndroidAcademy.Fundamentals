package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.JsonMovieMapper
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.network.RetrofitModule

interface MovieDetailsRepository {
    suspend fun loadMovie(id: Int): Movie
}

class NetworkMovieDetailsRepository(private val mapper: JsonMovieMapper) : MovieDetailsRepository {
    override suspend fun loadMovie(id: Int): Movie {
        val movieResponse = RetrofitModule.moviesApi.getMovie(id)
        return mapper.toMovie(movieResponse)
    }
}