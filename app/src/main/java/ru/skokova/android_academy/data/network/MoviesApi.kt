package ru.skokova.android_academy.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse

    @GET("movie/popular")
    suspend fun getMovies(): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int): MovieDetailsResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getActors(@Path("movie_id") id: Int): ActorsResponse
}