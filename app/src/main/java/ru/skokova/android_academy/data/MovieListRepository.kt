package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.converter.MovieMapper
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.network.RetrofitModule

interface MovieListRepository {
    suspend fun loadMovies(): List<Movie>
}

class NetworkMovieListRepository(private val mapper: MovieMapper) : MovieListRepository {
    override suspend fun loadMovies(): List<Movie> {
        val moviesResponse = RetrofitModule.moviesApi.getMovies()
        return moviesResponse.results.map {
            mapper.toMovie(it, GenresSource.genres)
        }
    }
}