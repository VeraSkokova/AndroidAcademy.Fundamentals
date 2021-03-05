package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.EntityGenreMapper
import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.persistent.MoviesDatabase

interface GenresCachingRepository : GenresRepository {
    suspend fun saveGenres(genres: List<Genre>)
}

class DbGenresRepository(private val mapper: EntityGenreMapper) : GenresCachingRepository {
    override suspend fun loadGenres(): List<Genre> {
        val savedGenres = MoviesDatabase.instance.genresDao.getAllGenres()
        return savedGenres.map(mapper::toGenre)
    }

    override suspend fun saveGenres(genres: List<Genre>) {
        val entities = genres.map(mapper::toEntity)
        MoviesDatabase.instance.genresDao.insertGenres(entities)
    }
}