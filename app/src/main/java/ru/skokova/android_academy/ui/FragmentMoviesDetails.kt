package ru.skokova.android_academy.ui

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.skokova.android_academy.R
import ru.skokova.android_academy.data.Resource
import ru.skokova.android_academy.data.model.Actor
import ru.skokova.android_academy.data.model.Movie
import ru.skokova.android_academy.data.model.MovieDetails
import ru.skokova.android_academy.ui.actor.ActorListDecoration
import ru.skokova.android_academy.ui.actor.AdapterActors
import ru.skokova.android_academy.viewmodel.*
import java.util.*

class FragmentMoviesDetails : Fragment() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val imageLoadingInfoViewModel: ImageLoadingInfoViewModel by viewModels { MovieViewModelFactory() }
    private val permissionViewModel: PermissionViewModel by viewModels()
    private lateinit var movieScheduleViewModel: MovieScheduleViewModel

    private val actorsAdapter = AdapterActors()

    private var navigationListener: MovieDetailsNavigationListener? = null

    private lateinit var progress: ProgressBar
    private lateinit var scheduleButton: AppCompatButton

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var isRationaleShown = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progress = view.findViewById(R.id.progress)
        view.findViewById<View>(R.id.tv_back)
            .setOnClickListener { navigationListener?.onBackPressed() }
        view.findViewById<View>(R.id.img_back)
            .setOnClickListener { navigationListener?.onBackPressed() }
        view.findViewById<RecyclerView>(R.id.rv_actors).apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = actorsAdapter
            addItemDecoration(
                ActorListDecoration(
                    context.resources.getDimension(R.dimen.actor_list_decor_margin).toInt()
                )
            )
        }
        scheduleButton = view.findViewById(R.id.btn_schedule)
        scheduleButton.setOnClickListener { onAddScheduleClick() }

        val movieId = arguments?.getInt(MOVIE_ID)
        movieId?.let {
            movieDetailsViewModel =
                ViewModelProvider(this, MovieDetailsViewModelFactory(movieId)).get(
                    MovieDetailsViewModel::class.java
                )
        } ?: run {
            Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show()
            navigationListener?.onBackPressed()
        }

        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> {
                    progress.visibility = View.GONE
                    allowToScheduleAMovieWatch(resource)
                    updateMovieInformation(view, resource.data)
                }
                is Resource.Error -> {
                    progress.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT)
                        .show()
                    navigationListener?.onBackPressed()
                }
                is Resource.Loading -> progress.visibility = View.VISIBLE
            }
        })

        permissionViewModel.rationaleShownLiveData.observe(viewLifecycleOwner, { rationaleShown ->
            isRationaleShown = rationaleShown
        })

        permissionViewModel.requestPermissionLiveData.observe(
            viewLifecycleOwner,
            { requestPermission ->
                if (requestPermission) {
                    requestPermission()
                }
            })
    }

    private fun allowToScheduleAMovieWatch(resource: Resource.Success<MovieDetails>) {
        movieScheduleViewModel =
            ViewModelProvider(this, MovieScheduleViewModelFactory(resource.data.movie))
                .get(MovieScheduleViewModel::class.java)
        scheduleButton.visibility = View.VISIBLE
    }

    private fun loadPosters(movie: Movie, actors: List<Actor>, view: View) {
        imageLoadingInfoViewModel.configurationLiveData.observe(
            viewLifecycleOwner,
            { resource ->
                when (resource) {
                    is Resource.Success -> {
                        progress.visibility = View.GONE
                        val backdropUrl = resource.data.baseBackdropImageUrl
                        val profileUrl = resource.data.baseProfileImageUrl
                        updatePosters(movie, actors, view, backdropUrl, profileUrl)
                    }
                    is Resource.Error -> {
                        progress.visibility = View.GONE
                        Toast.makeText(
                            context,
                            resource.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    is Resource.Loading -> progress.visibility = View.VISIBLE
                }
            }
        )
    }


    private fun updateMovieInformation(
        view: View,
        movieDetails: MovieDetails
    ) {
        val movie = movieDetails.movie
        val actors = movieDetails.actors

        view.findViewById<TextView>(R.id.tv_movie_name).text = movie.title
        view.findViewById<TextView>(R.id.tv_pg).text =
            context?.resources?.getString(R.string.pg, movie.minimumAge)
        view.findViewById<TextView>(R.id.tv_tags).text = movie.genres
        view.findViewById<RatingBar>(R.id.rating).rating = movie.ratings
        view.findViewById<TextView>(R.id.tv_reviews).text =
            context?.resources?.getQuantityString(
                R.plurals.movie_reviews,
                movie.numberOfRatings,
                movie.numberOfRatings
            )
        view.findViewById<TextView>(R.id.tv_description).text = movie.overview
        loadPosters(movie, actors, view)
    }

    private fun updatePosters(
        movie: Movie,
        actors: List<Actor>,
        view: View,
        backdropUrl: String?,
        profileUrl: String?
    ) {
        backdropUrl?.let {
            Glide.with(this)
                .load(backdropUrl + movie.backdrop)
                .apply(imageOption)
                .into(view.findViewById(R.id.iv_poster))
        }
        profileUrl?.let {
            actorsAdapter.updateActors(actors, profileUrl)
        } ?: run {
            Toast.makeText(context, R.string.actors_error, Toast.LENGTH_SHORT).show()
            view.findViewById<View>(R.id.tv_cast_title).visibility = View.GONE
        }
    }

    private fun onAddScheduleClick() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED -> showDateAndTimePickers()
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ->
                    showPermissionExplanationDialog()
                isRationaleShown -> showPermissionDeniedDialog()
                else -> requestPermission()
            }
        }
    }

    private fun showDateAndTimePickers() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, chosenYear, chosenMonth, chosenDay ->
                movieScheduleViewModel.setDate(chosenYear, chosenMonth, chosenDay)
                showTimePicker()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, chosenHour, chosenMinute ->
                movieScheduleViewModel.scheduleAWatch(chosenHour, chosenMinute)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun showPermissionExplanationDialog() {
        val explanationDialog = PermissionExplanationDialog()
        explanationDialog.show(childFragmentManager, PermissionExplanationDialog::class.simpleName)
    }

    private fun showPermissionDeniedDialog() {
        val permissionDeniedDialog = PermissionDeniedDialog()
        permissionDeniedDialog.show(childFragmentManager, PermissionDeniedDialog::class.simpleName)
    }

    private fun requestPermission() {
        context?.let {
            requestPermissionLauncher.launch(Manifest.permission.READ_CALENDAR)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = activity as MovieDetailsNavigationListener
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showDateAndTimePickers()
            } else {
                Toast.makeText(context, R.string.calendar_permission_denied, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDetach() {
        navigationListener = null
        requestPermissionLauncher.unregister()
        super.onDetach()
    }

    interface MovieDetailsNavigationListener {
        fun onBackPressed()
    }

    companion object {
        private const val MOVIE_ID = "movie_id"
        const val TAG = "MOVIE_DETAILS"

        private val imageOption = RequestOptions()
            .placeholder(R.drawable.ic_baseline_movie)
            .fallback(R.drawable.ic_baseline_movie)
            .centerCrop()

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}