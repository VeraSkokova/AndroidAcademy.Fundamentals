package ru.skokova.android_academy.ui.movie

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MoviesListDecoration(private val columns: Int, private val spacing: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val column = position % columns

        if (position < columns) {
            outRect.top = spacing
        }
        outRect.bottom = spacing

        val multiplier = spacing / columns
        outRect.left = spacing - column * multiplier
        outRect.right = (column + 1) * multiplier
    }
}