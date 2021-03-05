package ru.skokova.android_academy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseUpdateWorker(applicationContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.d("TAG", "root doWork() called")
        val imageLoadingInfoUpdateRequest =
            OneTimeWorkRequestBuilder<ImageLoadingInfoUpdateWorker>()
                .build()
        val genresUpdateRequest = OneTimeWorkRequestBuilder<GenresUpdateWorker>()
            .build()
        val movieListUpdateRequest = OneTimeWorkRequestBuilder<MovieListUpdateWorker>()
            .build()
        val movieDetailsUpdateRequest = OneTimeWorkRequestBuilder<MovieDetailsUpdateWorker>()
            .build()
        val actorsUpdateWorker = OneTimeWorkRequestBuilder<ActorsUpdateWorker>()
            .build()

        WorkManager.getInstance(applicationContext)
            .beginWith(imageLoadingInfoUpdateRequest)
            .then(genresUpdateRequest)
            .then(movieListUpdateRequest)
            .then(listOf(movieDetailsUpdateRequest, actorsUpdateWorker))
            .enqueue()

        Result.success()
    }
}