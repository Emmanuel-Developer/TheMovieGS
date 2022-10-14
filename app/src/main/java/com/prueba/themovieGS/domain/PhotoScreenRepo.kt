package com.prueba.themovieGS.domain

import com.prueba.themovieGS.base.Resources
import com.prueba.themovieGS.data.entity.ImageList

interface PhotoScreenRepo {

    suspend fun getLatestImages(): Resources<List<ImageList>>
}
