package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.JsonGenreMapper
import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.network.RetrofitModule

interface GenresRepository {
    suspend fun loadGenres(): List<Genre>
}

class NetworkGenresRepository(private val jsonMapper: JsonGenreMapper) : GenresRepository {
    override suspend fun loadGenres(): List<Genre> {
        return RetrofitModule.moviesApi.getGenres().genres.map { jsonMapper.toGenre(it) }
    }
}