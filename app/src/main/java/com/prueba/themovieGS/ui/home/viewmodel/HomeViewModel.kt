package com.prueba.themovieGS.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prueba.themovieGS.base.Resources
import com.prueba.themovieGS.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeViewModel(private val repo: MovieRepository) : ViewModel() {

    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resources.Loading())

        try {
            emit(Resources.Success(Pair(repo.getUpcomingMovies(), repo.getNowPlayingMovies())))
        } catch (e: Exception) {
            emit(Resources.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}
