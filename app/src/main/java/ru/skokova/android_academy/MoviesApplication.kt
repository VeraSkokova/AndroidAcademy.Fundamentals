package ru.skokova.android_academy

import android.app.Application
import android.content.Context
import androidx.work.*
import ru.skokova.android_academy.workers.DatabaseUpdateWorker
import ru.skokova.android_academy.workers.MoviesWorkerFactory
import ru.skokova.android_academy.workers.WorkerUtils
import java.util.concurrent.TimeUnit

class MoviesApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        setupCacheUpdate()
    }

    private fun setupCacheUpdate() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val databaseUpdateRequest =
            PeriodicWorkRequestBuilder<DatabaseUpdateWorker>(
                WorkerUtils.UPDATE_INTERVAL,
                TimeUnit.HOURS
            ).setConstraints(constraints)
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(databaseUpdateRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(MoviesWorkerFactory())
            .build()

    companion object {
        lateinit var context: Context
    }
}