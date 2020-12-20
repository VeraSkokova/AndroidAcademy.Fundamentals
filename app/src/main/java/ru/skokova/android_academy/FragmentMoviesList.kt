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
import kotlinx.coroutines.*
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

    override fun onDestroyView() {
        scope.cancel()
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + exceptionHandler)
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        context?.let {
            scope.launch {
                val loadMovies = loadMovies(it)
                moviesAdapter?.updateMovies(loadMovies)
            }
        }
    }

    interface MovieClickListener {
        fun onClick(id: Int)
    }

    companion object {
        private const val COLUMNS_COUNT = 2
        private const val ERROR_TAG = "COROUTINE_ERROR"
    }
}