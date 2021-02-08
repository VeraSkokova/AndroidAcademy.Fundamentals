package ru.skokova.android_academy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.ActorsCachingRepository
import ru.skokova.android_academy.data.ActorsRepository

class ActorsUpdateWorker(
    applicationContext: Context,
    workerParams: WorkerParameters,
    private val actorsRepository: ActorsRepository,
    private val actorsCachingRepository: ActorsCachingRepository
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("TAG", "actors doWork() called")
        actorsCachingRepository.deleteOldActors()
        val moviesIds = inputData.getIntArray(WorkerUtils.IDS_KEY)
        moviesIds?.forEach { movieId ->
            val actors = actorsRepository.getActors(movieId)
            actorsCachingRepository.saveActors(movieId, actors)
        }

        return Result.success()
    }
}