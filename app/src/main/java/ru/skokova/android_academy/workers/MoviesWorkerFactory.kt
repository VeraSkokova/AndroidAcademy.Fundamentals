package ru.skokova.android_academy.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.*
import ru.skokova.android_academy.data.mapper.*

class MoviesWorkerFactory : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = when (workerClassName) {
        DatabaseUpdateWorker::class.java.name -> DatabaseUpdateWorker(appContext, workerParameters)
        ImageLoadingInfoUpdateWorker::class.java.name -> ImageLoadingInfoUpdateWorker(
            appContext,
            workerParameters,
            NetworkImageLoadingInfoRepository(JsonImageLoadingInfoMapper())
        )
        GenresUpdateWorker::class.java.name -> GenresUpdateWorker(
            appContext,
            workerParameters,
            NetworkGenresRepository(JsonGenreMapper()),
            DbGenresRepository(EntityGenreMapper())
        )
        MovieListUpdateWorker::class.java.name -> MovieListUpdateWorker(
            appContext,
            workerParameters,
            NetworkMovieListRepository(JsonMovieMapper(), DbGenresRepository(EntityGenreMapper())),
            DbMovieListRepository(EntityMovieMapper())
        )
        MovieDetailsUpdateWorker::class.java.name -> MovieDetailsUpdateWorker(
            appContext,
            workerParameters,
            NetworkMovieDetailsRepository(JsonMovieMapper()),
            DbMovieDetailsRepository(EntityMovieMapper())
        )
        ActorsUpdateWorker::class.java.name -> ActorsUpdateWorker(
            appContext,
            workerParameters,
            NetworkActorsRepository(JsonActorsMapper()),
            DbActorsRepository(EntityActorsMapper())
        )
        else -> throw IllegalStateException("Unknown worker with name $workerClassName")
    }
}