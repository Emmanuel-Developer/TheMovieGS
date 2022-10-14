package com.prueba.themovieGS.data.local

import com.prueba.themovieGS.data.entity.MovieEntity
import com.prueba.themovieGS.data.entity.MovieList
import com.prueba.themovieGS.data.entity.toMovieList

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getNowPlayingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "now_playing" }.toMovieList()
    }

    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }
}
