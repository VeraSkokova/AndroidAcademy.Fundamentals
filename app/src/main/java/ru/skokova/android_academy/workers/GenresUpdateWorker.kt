package ru.skokova.android_academy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.GenresCachingRepository
import ru.skokova.android_academy.data.GenresRepository

class GenresUpdateWorker(
    applicationContext: Context,
    workerParams: WorkerParameters,
    private val genresRepository: GenresRepository,
    private val genresCachingRepository: GenresCachingRepository
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("TAG", "genres doWork() called")
        val genres = genresRepository.loadGenres()
        genresCachingRepository.saveGenres(genres)

        return Result.success()
    }
}