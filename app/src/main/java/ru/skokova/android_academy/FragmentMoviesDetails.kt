package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentMoviesDetails : Fragment() {
    private var navigationListener: MovieDetailsNavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tv_back)
            .setOnClickListener { navigationListener?.onBackPressed() }
        view.findViewById<ImageView>(R.id.img_back)
            .setOnClickListener { navigationListener?.onBackPressed() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = activity as MovieDetailsNavigationListener
    }

    override fun onDetach() {
        super.onDetach()
        navigationListener = null
    }

    interface MovieDetailsNavigationListener {
        fun onBackPressed()
    }
}