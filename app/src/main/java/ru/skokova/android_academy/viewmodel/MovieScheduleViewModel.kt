package ru.skokova.android_academy.viewmodel

import androidx.lifecycle.ViewModel
import ru.skokova.android_academy.business.MovieWatchUseCase
import ru.skokova.android_academy.data.model.Movie
import java.util.*

class MovieScheduleViewModel(
    private val movie: Movie,
    private val movieWatchUseCase: MovieWatchUseCase
) : ViewModel() {
    private val calendar = Calendar.getInstance()

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
    }

    fun scheduleAWatch(hour: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val startTime = calendar.timeInMillis
        movieWatchUseCase.scheduleAWatch(movie, startTime)
    }
}