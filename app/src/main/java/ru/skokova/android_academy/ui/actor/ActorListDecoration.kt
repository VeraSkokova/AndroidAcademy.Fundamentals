package ru.skokova.android_academy.ui.actor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ActorListDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.left = space
        }
    }
}