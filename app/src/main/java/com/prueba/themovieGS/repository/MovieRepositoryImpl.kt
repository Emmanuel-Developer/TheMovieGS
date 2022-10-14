package com.prueba.themovieGS.repository

import com.prueba.themovieGS.base.InternetCheck
import com.prueba.themovieGS.data.local.LocalMovieDataSource
import com.prueba.themovieGS.data.entity.MovieList
import com.prueba.themovieGS.data.entity.toMovieEntity
import com.prueba.themovieGS.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getNowPlayingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getNowPlayingMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("now_playing"))
            }
            dataSourceLocal.getNowPlayingMovies()
        } else {
            dataSourceLocal.getNowPlayingMovies()
        }
    }

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getUpcomingMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }
}
