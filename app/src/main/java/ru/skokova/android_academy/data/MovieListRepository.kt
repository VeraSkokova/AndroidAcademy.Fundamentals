package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.mapper.JsonMovieMapper
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.network.MoviesApi

interface MovieListRepository {
    suspend fun loadMovies(): List<Movie>
}

class NetworkMovieListRepository(
    private val moviesApi: MoviesApi,
    private val mapper: JsonMovieMapper,
    private val genresRepository: GenresRepository
) : MovieListRepository {

    override suspend fun loadMovies(): List<Movie> {
        val moviesResponse = moviesApi.getMovies()
        val genres = genresRepository.loadGenres().associateBy { genre -> genre.id }
        return moviesResponse.results.map {
            mapper.toMovie(it, genres)
        }
    }
}