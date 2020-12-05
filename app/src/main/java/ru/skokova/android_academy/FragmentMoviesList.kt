package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class FragmentMoviesList : Fragment() {
    private var clickListener: MovieClickListener? = null
    private var moviesAdapter: AdapterMovies? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = AdapterMovies(clickListener)
        view.findViewById<RecyclerView>(R.id.rv_movies).apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = moviesAdapter
            addItemDecoration(MoviesListDecoration(2, Metrics.dpToPx(12)))
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

    override fun onStart() {
        super.onStart()
        moviesAdapter?.bindMovies(MovieGenerator(context).getMovies())
    }

    private fun setMovieClickListener(listener: MovieClickListener?) {
        clickListener = listener
    }

    interface MovieClickListener {
        fun onClick(id: Int)
    }
}