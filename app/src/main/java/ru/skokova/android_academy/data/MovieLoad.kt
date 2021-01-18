package ru.skokova.android_academy.data

import ru.skokova.android_academy.data.network.MovieDetailsResponse
import ru.skokova.android_academy.data.network.MovieListResponse
import ru.skokova.android_academy.data.network.RetrofitModule

interface MovieListRepository {
    suspend fun loadMovies(): MovieListResponse
}

class NetworkMovieLoader : MovieListRepository {
    override suspend fun loadMovies(): MovieListResponse {
        return RetrofitModule.moviesApi.getMovies()
    }
}

interface MovieDetailsRepository {
    suspend fun loadMovie(id: Int): MovieDetailsResponse
}

class NetworkMovieDetailsLoader : MovieDetailsRepository {
    override suspend fun loadMovie(id: Int): MovieDetailsResponse {
        return RetrofitModule.moviesApi.getMovie(id)
    }
}