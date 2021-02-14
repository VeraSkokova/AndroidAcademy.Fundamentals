package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.JsonGenreMapper
import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.network.MoviesApi

interface GenresRepository {
    suspend fun loadGenres(): List<Genre>
}

class NetworkGenresRepository(
    private val moviesApi: MoviesApi,
    private val jsonMapper: JsonGenreMapper
) : GenresRepository {
    override suspend fun loadGenres(): List<Genre> {
        return moviesApi.getGenres().genres.map(jsonMapper::toGenre)
    }
}