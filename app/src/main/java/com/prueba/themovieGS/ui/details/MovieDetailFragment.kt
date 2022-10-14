package com.prueba.themovieGS.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.prueba.themovieGS.R
import com.prueba.themovieGS.databinding.FragmentMovieDetailBinding
import com.prueba.themovieGS.manager.Constants.TMDB_KEY

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext())
            .load("$TMDB_KEY${args.backgroundImageUrl}").centerCrop()
            .into(binding.imgBackground)
        binding.description.text = args.overview
        binding.txtTitleMovie.text = args.title
        binding.txtRelease.text =
            binding.root.context.getString(R.string.released, args.releaseDate)
        binding.txtLanguage.text = binding.root.context.getString(R.string.language, args.language)
        binding.txtPopular.text = binding.root.context.getString(
            R.string.reviews,
            "${args.voteAverage} (${args.voteCount})"
        )
    }
}
