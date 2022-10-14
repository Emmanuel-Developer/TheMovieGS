package com.prueba.themovieGS.ui.home

import com.prueba.themovieGS.base.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.prueba.themovieGS.R
import com.prueba.themovieGS.data.local.AppDatabase
import com.prueba.themovieGS.data.local.LocalMovieDataSource
import com.prueba.themovieGS.data.entity.Movie
import com.prueba.themovieGS.data.remote.RemoteMovieDataSource
import com.prueba.themovieGS.databinding.FragmentMovieBinding
import com.prueba.themovieGS.ui.home.viewmodel.HomeViewModel
import com.prueba.themovieGS.ui.home.viewmodel.MovieViewModelFactory
import com.prueba.themovieGS.repository.MovieRepositoryImpl
import com.prueba.themovieGS.repository.RetrofitClient
import com.prueba.themovieGS.ui.home.adapters.adapter.HomeAdapter
import com.prueba.themovieGS.ui.home.adapters.repository.OnMovieListener
import com.prueba.themovieGS.ui.home.adapters.adapter.NowPlayingAdapter
import com.prueba.themovieGS.ui.home.adapters.adapter.UpcomingAdapter

class HomeFragment : Fragment(R.layout.fragment_movie), OnMovieListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<HomeViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resources.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resources.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingAdapter(
                                HomeAdapter(
                                    result.data.first.results,
                                    this@HomeFragment
                                )
                            )
                        )
                        addAdapter(
                            0,
                            NowPlayingAdapter(
                                HomeAdapter(
                                    result.data.second.results,
                                    this@HomeFragment
                                )
                            )
                        )
                        addAdapter(
                            0,
                            UpcomingAdapter(
                                HomeAdapter(
                                    result.data.first.results,
                                    this@HomeFragment
                                )
                            )
                        )
                    }
                    binding.rvMovie.adapter = concatAdapter
                }
                is Resources.Failure -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val action = HomeFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date,
            movie.vote_average.toFloat(),
            movie.vote_count
        )
        findNavController().navigate(action)
    }
}
