package com.prueba.themovieGS.repository

import com.prueba.themovieGS.data.entity.MovieList

interface MovieRepository {

    suspend fun getNowPlayingMovies(): MovieList

    suspend fun getUpcomingMovies(): MovieList
}
