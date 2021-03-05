package ru.skokova.android_academy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.MovieDetailsCachingRepository
import ru.skokova.android_academy.data.MovieDetailsRepository

class MovieDetailsUpdateWorker(
    applicationContext: Context,
    workerParams: WorkerParameters,
    private val movieDetailsRepository: MovieDetailsRepository,
    private val movieDetailsCachingRepository: MovieDetailsCachingRepository
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("TAG", "movie details doWork() called")
        val moviesIds = inputData.getIntArray(WorkerUtils.IDS_KEY)
        moviesIds?.forEach { id ->
            val movie = movieDetailsRepository.loadMovie(id)
            movieDetailsCachingRepository.saveMovie(movie)
        }
        return Result.success()
    }
}