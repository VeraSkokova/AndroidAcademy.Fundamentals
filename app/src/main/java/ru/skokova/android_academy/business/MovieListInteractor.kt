package ru.skokova.android_academy.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.skokova.android_academy.data.GenresRepository
import ru.skokova.android_academy.data.GenresSource
import ru.skokova.android_academy.data.MovieListRepository
import ru.skokova.android_academy.data.converter.GenreMapper
import ru.skokova.android_academy.data.converter.MovieMapper
import ru.skokova.android_academy.data.model.Movie

class MovieListInteractor(
    private val movieListRepository: MovieListRepository,
    private val genresRepository: GenresRepository
) {
    suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        if (GenresSource.genres.isEmpty()) {
            val loadGenres = genresRepository.loadGenres()
            val genreConverter = GenreMapper()
            GenresSource.saveGenres(loadGenres.genres.map { genreConverter.toGenre(it) })
        }

        val movieConverter = MovieMapper()
        movieListRepository.loadMovies().results.map {
            movieConverter.toMovie(it, GenresSource.genres)
        }
    }
}