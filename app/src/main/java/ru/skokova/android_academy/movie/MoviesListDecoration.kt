package ru.skokova.android_academy.movie

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MoviesListDecoration(private val columns: Int, private val spacing: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val column = position % columns

        with(outRect) {
            if (position < columns) {
                top = spacing
            }
            bottom = spacing

            left = spacing - column * spacing / columns
            right = (column + 1) * spacing / columns
        }
    }
}