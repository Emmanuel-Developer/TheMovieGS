package com.prueba.themovieGS.data.remote

import com.prueba.themovieGS.manager.Constants
import com.prueba.themovieGS.data.entity.MovieList
import com.prueba.themovieGS.repository.WebService

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getNowPlayingMovies(): MovieList {
        return webService.getNowplayingMovies(Constants.API_KEY)
    }

    suspend fun getUpcomingMovies(): MovieList {
        return webService.getUpcomingMovies(Constants.API_KEY)
    }
}
