package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.skokova.android_academy.movie.AdapterMovies
import ru.skokova.android_academy.movie.MovieGenerator
import ru.skokova.android_academy.movie.MoviesListDecoration

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
            layoutManager = GridLayoutManager(context, COLUMNS_COUNT, RecyclerView.VERTICAL, false)
            adapter = moviesAdapter
            addItemDecoration(
                MoviesListDecoration(
                    2,
                    context.resources.getDimension(R.dimen.movie_list_decor_margin).toInt()
                )
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = activity as MovieClickListener
    }

    override fun onDetach() {
        clickListener = null
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()
        moviesAdapter?.updateMovies(MovieGenerator.getMovies())
    }

    interface MovieClickListener {
        fun onClick(id: Int)
    }

    companion object {
        private const val COLUMNS_COUNT = 2
    }
}