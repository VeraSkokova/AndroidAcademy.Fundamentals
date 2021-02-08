package ru.skokova.android_academy.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.skokova.android_academy.data.ImageLoadingInfoRepository

class ImageLoadingInfoUpdateWorker(
    applicationContext: Context,
    workerParams: WorkerParameters,
    private val imageLoadingInfoRepository: ImageLoadingInfoRepository
) : CoroutineWorker(applicationContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("TAG", "image urls doWork() called")
        imageLoadingInfoRepository.getImageLoadingInfo()
        return Result.success()
    }
}