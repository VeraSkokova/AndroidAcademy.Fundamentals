package ru.skokova.android_academy.data

import ru.skokova.android_academy.MoviesApplication

interface MovieListRepository {
    fun loadMovies(): List<Movie>
}

class JsonMovieLoader : MovieListRepository {
    override fun loadMovies(): List<Movie> {
        return loadMovies(context = MoviesApplication.context)
    }
}

interface MovieDetailsRepository {
    fun loadMovie(id: Int): Movie?
}

class JsonMovieDetailsLoader : MovieDetailsRepository {
    override fun loadMovie(id: Int): Movie? {
        return loadMovie(id, MoviesApplication.context)
    }
}