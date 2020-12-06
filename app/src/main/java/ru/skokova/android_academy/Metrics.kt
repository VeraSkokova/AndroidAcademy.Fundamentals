package ru.skokova.android_academy

import android.content.res.Resources

class Metrics {
    companion object {
        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}