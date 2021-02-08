package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.EntityGenreMapper
import ru.skokova.android_academy.data.model.Genre
import ru.skokova.android_academy.data.persistent.MoviesDatabase

interface GenresCachingRepository : GenresRepository {
    suspend fun saveGenres(genres: List<Genre>)
}

class DbGenresRepository(private val mapper: EntityGenreMapper) : GenresCachingRepository {
    private val moviesDatabase = MoviesDatabase.instance

    override suspend fun loadGenres(): List<Genre> {
        val savedGenres = moviesDatabase.genresDao.getAllGenres()
        return savedGenres.map { mapper.toGenre(it) }
    }

    override suspend fun saveGenres(genres: List<Genre>) {
        val entities = genres.map { mapper.toEntity(it) }
        moviesDatabase.genresDao.deleteGenres()
        moviesDatabase.genresDao.insertGenres(entities)
    }
}