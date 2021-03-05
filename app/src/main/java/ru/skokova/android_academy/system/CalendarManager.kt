package ru.skokova.android_academy.system

import android.content.Intent
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import ru.skokova.android_academy.MoviesApplication

class CalendarManager {
    fun insertEvent(startTime: Long, endTime: Long, title: String, description: String) {
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.DESCRIPTION, description)
            .putExtra(
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.AVAILABILITY_BUSY
            )
        ContextCompat.startActivity(MoviesApplication.context, intent, null)
    }
}