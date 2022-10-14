package com.prueba.themovieGS.ui.home.adapters.holder

import android.content.Context
import com.bumptech.glide.Glide
import com.prueba.themovieGS.base.BaseViewHolder
import com.prueba.themovieGS.data.entity.Movie
import com.prueba.themovieGS.databinding.ItemGalleryBinding
import com.prueba.themovieGS.manager.Constants

class MoviesViewHolder(val binding: ItemGalleryBinding, val context: Context) :
    BaseViewHolder<Movie>(binding.root) {
    override fun bind(item: Movie) {
        Glide.with(context).load("${Constants.TMDB_KEY}${item.poster_path}")
            .centerCrop().into(binding.imgMovie)
    }
}
