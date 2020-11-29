package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class FragmentMoviesList: Fragment() {
    private var clickListener: MovieClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<MaterialCardView>(R.id.cv_movie).apply {
            setOnClickListener { clickListener?.onClick() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setMovieClickListener(activity as MovieClickListener)
    }

    override fun onDetach() {
        super.onDetach()
        setMovieClickListener(null)
    }

    private fun setMovieClickListener(listener: MovieClickListener?) {
        clickListener = listener
    }

    interface MovieClickListener {
        fun onClick()
    }
}