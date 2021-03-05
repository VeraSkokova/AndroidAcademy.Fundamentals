package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.EntityMovieMapper
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.persistent.MoviesDatabase

interface MovieDetailsCachingRepository : MovieDetailsRepository {
    suspend fun saveMovie(movie: Movie)
}

class DbMovieDetailsRepository(private val mapper: EntityMovieMapper) : MovieDetailsCachingRepository {
    override suspend fun saveMovie(movie: Movie) {
        val movieEntity = mapper.toEntity(movie)
        MoviesDatabase.instance.moviesDao.updateMovie(movieEntity)
    }

    override suspend fun loadMovie(id: Int): Movie {
        val database = MoviesDatabase.instance
        val movieEntity = database.moviesDao.getMovie(id)
        return mapper.toMovie(movieEntity)
    }
}