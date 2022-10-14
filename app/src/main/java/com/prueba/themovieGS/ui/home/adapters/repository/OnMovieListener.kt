package com.prueba.themovieGS.ui.home.adapters.repository

import com.prueba.themovieGS.data.entity.Movie

interface OnMovieListener {

    fun onMovieClick(movie: Movie)
}
