package com.prueba.themovieGS.ui.gallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prueba.themovieGS.base.Resources
import com.prueba.themovieGS.domain.PhotoScreenRepo
import kotlinx.coroutines.Dispatchers

class GalleryViewModel(private val repo: PhotoScreenRepo) : ViewModel() {

    fun fetchLatestImages() = liveData(Dispatchers.IO) {
        emit(Resources.Loading())
        try {
            emit(repo.getLatestImages())
        } catch (e: Exception) {
            emit(Resources.Failure(e))
        }
    }
}

class PhotoScreenViewModelFactory(private val repo: PhotoScreenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PhotoScreenRepo::class.java).newInstance(repo)
    }
}
