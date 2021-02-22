package ru.skokova.android_academy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.MovieListCachingRepository
import ru.skokova.android_academy.data.MovieListRepository
import ru.skokova.android_academy.data.NotificationsHelper
import ru.skokova.android_academy.data.model.Movie

class MovieListUpdateWorker(
    applicationContext: Context,
    workerParams: WorkerParameters,
    private val movieListRepository: MovieListRepository,
    private val movieListCachingRepository: MovieListCachingRepository,
    private val notificationsHelper: NotificationsHelper
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("TAG", "movie list doWork() called")
        val movies = movieListRepository.loadMovies()
        movieListCachingRepository.saveMovies(movies)

        val topMovie = movies.maxByOrNull { movie -> movie.ratings }
        topMovie?.let { notificationsHelper.showNotification(it) }

        val outputData = createOutputData(movies)
        return Result.success(outputData)
    }

    private fun createOutputData(movies: List<Movie>): Data {
        val ids = movies.map { movie -> movie.id }.toIntArray()
        return Data.Builder()
            .putIntArray(WorkerUtils.IDS_KEY, ids)
            .build()
    }
}