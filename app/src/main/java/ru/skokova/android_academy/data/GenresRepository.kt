package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.converter.GenreMapper
import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.network.RetrofitModule

interface GenresRepository {
    suspend fun loadGenres(): List<Genre>
}

class NetworkGenresRepository(private val mapper: GenreMapper) : GenresRepository {
    override suspend fun loadGenres(): List<Genre> {
        val genres = RetrofitModule.moviesApi.getGenres().genres.map { mapper.toGenre(it) }
        GenresSource.saveGenres(genres)
        return genres
    }
}