package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.EntityMovieMapper
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.persistent.MoviesDatabase

interface MovieListCachingRepository : MovieListRepository {
    suspend fun saveMovies(movies: List<Movie>)
}

class DbMovieListRepository(private val mapper: EntityMovieMapper) : MovieListCachingRepository {
    private val moviesDatabase = MoviesDatabase.instance

    override suspend fun loadMovies(): List<Movie> {
        val database = moviesDatabase
        val savedMovies = database.moviesDao.getAllMovies()
        return savedMovies.map { movieEntity ->
            mapper.toMovie(movieEntity)
        }
    }

    override suspend fun saveMovies(movies: List<Movie>) {
        val entities = movies.map { movie ->
            mapper.toEntity(movie)
        }
        moviesDatabase.moviesDao.deleteMovies()
        moviesDatabase.moviesDao.insertMovies(entities)
    }
}