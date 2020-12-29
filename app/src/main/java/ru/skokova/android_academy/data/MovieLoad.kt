package ru.skokova.android_academy.data

import ru.skokova.android_academy.MoviesApplication

interface MovieListRepository {
    suspend fun loadMovies(): List<Movie>
}

class JsonMovieLoader : MovieListRepository {
    override suspend fun loadMovies(): List<Movie> {
        return loadMovies(context = MoviesApplication.context)
    }
}

interface MovieDetailsRepository {
    suspend fun loadMovie(id: Int): Movie?
}

class JsonMovieDetailsLoader : MovieDetailsRepository {
    override suspend fun loadMovie(id: Int): Movie? {
        return loadMovie(id, MoviesApplication.context)
    }
}