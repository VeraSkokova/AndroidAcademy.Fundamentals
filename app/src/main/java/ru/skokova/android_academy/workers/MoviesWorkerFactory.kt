package ru.skokova.android_academy.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.*
import ru.skokova.android_academy.data.mapper.*
import ru.skokova.android_academy.data.network.RetrofitModule

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
            NetworkImageLoadingInfoRepository(
                RetrofitModule.moviesApi,
                JsonImageLoadingInfoMapper()
            )
        )
        GenresUpdateWorker::class.java.name -> GenresUpdateWorker(
            appContext,
            workerParameters,
            NetworkGenresRepository(RetrofitModule.moviesApi, JsonGenreMapper()),
            DbGenresRepository(EntityGenreMapper())
        )
        MovieListUpdateWorker::class.java.name -> MovieListUpdateWorker(
            appContext,
            workerParameters,
            NetworkMovieListRepository(
                RetrofitModule.moviesApi,
                JsonMovieMapper(),
                DbGenresRepository(EntityGenreMapper())
            ),
            DbMovieListRepository(EntityMovieMapper())
        )
        MovieDetailsUpdateWorker::class.java.name -> MovieDetailsUpdateWorker(
            appContext,
            workerParameters,
            NetworkMovieDetailsRepository(RetrofitModule.moviesApi, JsonMovieMapper()),
            DbMovieDetailsRepository(EntityMovieMapper())
        )
        ActorsUpdateWorker::class.java.name -> ActorsUpdateWorker(
            appContext,
            workerParameters,
            NetworkActorsRepository(RetrofitModule.moviesApi, JsonActorsMapper()),
            DbActorsRepository(EntityActorsMapper())
        )
        else -> throw IllegalStateException("Unknown worker with name $workerClassName")
    }
}