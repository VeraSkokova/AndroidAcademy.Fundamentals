package ru.skokova.android_academy.data

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.ui.MainActivity

class NotificationsHelper(private val context: Context) {
    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        notificationManagerCompat.createNotificationChannel(
            NotificationChannelCompat.Builder(
                CHANNEL_TOP_MOVIE,
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .setName(context.getString(R.string.channel_top_movie))
                .setDescription(context.getString(R.string.channel_top_movie_description))
                .build()
        )
    }

    fun showNotification(movie: Movie) {
        val contentUri = "https://themoviedb.org/movie/${movie.id}".toUri()
        val movieIntent = Intent(context, MainActivity::class.java).setAction(Intent.ACTION_VIEW)
            .setData(contentUri)
        val pendingMovieIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            movieIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_TOP_MOVIE)
            .setContentTitle(movie.title)
            .setContentText(movie.overview)
            .setSmallIcon(R.drawable.ic_baseline_movie)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setContentIntent(pendingMovieIntent)
            .build()

        notificationManagerCompat.notify(TOP_MOVIE_TAG, movie.id, notification)
    }

    companion object {
        const val CHANNEL_TOP_MOVIE = "channel_top_movie"
        const val REQUEST_CODE = 1
        const val TOP_MOVIE_TAG = "top_movie"
    }
}