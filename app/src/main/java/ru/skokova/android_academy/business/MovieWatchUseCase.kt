package ru.skokova.android_academy.business

import ru.skokova.android_academy.MoviesApplication
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.system.CalendarManager

class MovieWatchUseCase(private val calendarManager: CalendarManager) {
    fun scheduleAWatch(movie: Movie, startTime: Long) {
        val endTime = startTime + movie.runtime * 60000
        calendarManager.insertEvent(
            startTime,
            endTime,
            movie.title,
            MoviesApplication.context.getString(R.string.watch_movie_description)
        )
    }
}