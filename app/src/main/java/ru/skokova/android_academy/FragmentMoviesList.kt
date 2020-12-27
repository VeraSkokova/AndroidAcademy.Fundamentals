package ru.skokova.android_academy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import kotlinx.coroutines.*
import ru.skokova.android_academy.data.Movie
import ru.skokova.android_academy.data.loadMovies
import ru.skokova.android_academy.movie.AdapterMovies
import ru.skokova.android_academy.movie.MoviesListDecoration


class FragmentMoviesList : Fragment() {
    private var clickListener: MovieClickListener? = null
    private var moviesAdapter: AdapterMovies? = null

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show()
        Log.w(ERROR_TAG, "CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + exceptionHandler)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = AdapterMovies(clickListener, Glide.with(this))
        moviesAdapter?.setHasStableIds(true)
        val preloadSizeProvider = ViewPreloadSizeProvider<Movie>()
        val recyclerViewPreloader = RecyclerViewPreloader(
            Glide.with(this),
            moviesAdapter!!,
            preloadSizeProvider,
            PRELOAD_COUNT
        )
        view.findViewById<RecyclerView>(R.id.rv_movies).apply {
            layoutManager = GridLayoutManager(context, COLUMNS_COUNT, RecyclerView.VERTICAL, false)
            adapter = moviesAdapter
            addItemDecoration(
                MoviesListDecoration(
                    COLUMNS_COUNT,
                    context.resources.getDimension(R.dimen.movie_list_decor_margin).toInt()
                )
            )
            addOnScrollListener(recyclerViewPreloader)
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

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        scope.launch {
            val loadMovies = loadMovies(requireContext())
            moviesAdapter?.updateMovies(loadMovies)
        }
    }

    interface MovieClickListener {
        fun onClick(id: Int)
    }

    companion object {
        private const val COLUMNS_COUNT = 2
        private const val ERROR_TAG = "COROUTINE_ERROR"
        private const val PRELOAD_COUNT = 7
    }
}