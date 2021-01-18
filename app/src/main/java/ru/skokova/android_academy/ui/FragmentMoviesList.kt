package ru.skokova.android_academy.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.ui.movie.AdapterMovies
import ru.skokova.android_academy.ui.movie.MoviesListDecoration
import ru.skokova.android_academy.viewmodel.MovieListViewModel
import ru.skokova.android_academy.viewmodel.MovieViewModelFactory
import ru.skokova.android_academy.viewmodel.TmdbConfigurationViewModel


class FragmentMoviesList : Fragment() {

    private val moviesViewModel: MovieListViewModel by viewModels { MovieViewModelFactory() }
    private val configurationViewModel: TmdbConfigurationViewModel by viewModels { MovieViewModelFactory() }

    private var clickListener: MovieClickListener? = null
    private var configurationListener: ConfigurationListener? = null

    private var moviesAdapter: AdapterMovies? = null

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

        configurationListener?.getPosterImageUrl()?.apply {
            getMovies()
        } ?: run {
            configurationViewModel.configurationLiveData.observe(
                viewLifecycleOwner,
                { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            configurationListener?.setBaseImageUrls(
                                resource.data.baseBackdropImageUrl,
                                resource.data.basePosterImageUrl,
                                resource.data.baseProfileImageUrl
                            )
                            getMovies()
                        }
                        is Resource.Error -> Toast.makeText(
                            context,
                            resource.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            )
        }
    }

    private fun getMovies() {
        val baseUrl = configurationListener?.getPosterImageUrl() ?: return

        moviesViewModel.moviesLiveData.observe(
            viewLifecycleOwner,
            { resource ->
                when (resource) {
                    is Resource.Success -> {
                        moviesAdapter?.updateMovies(resource.data, baseUrl)
                    }
                    is Resource.Error -> Toast.makeText(
                        context,
                        resource.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = activity as MovieClickListener
        configurationListener = activity as ConfigurationListener
    }

    override fun onDetach() {
        clickListener = null
        configurationListener = null
        super.onDetach()
    }

    interface MovieClickListener {
        fun onClick(id: Int)
    }

    interface ConfigurationListener {
        fun getPosterImageUrl(): String?

        fun setBaseImageUrls(backdropUrl: String, posterUrl: String, profileUrl: String)
    }

    companion object {
        private const val COLUMNS_COUNT = 2
        private const val PRELOAD_COUNT = 7
    }
}