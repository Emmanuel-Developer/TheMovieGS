package com.prueba.themovieGS.domain

import com.prueba.themovieGS.base.Resources
import com.prueba.themovieGS.data.entity.ImageList
import com.prueba.themovieGS.data.remote.PhotoScreenDataSource

class PhotoScreenRepoImpl(private val dataSource: PhotoScreenDataSource) : PhotoScreenRepo {

    override suspend fun getLatestImages(): Resources<List<ImageList>> =
        dataSource.getLatestImages()
}
