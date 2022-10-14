package com.prueba.themovieGS.data.remote

import com.prueba.themovieGS.base.Resources
import com.prueba.themovieGS.data.entity.ImageList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PhotoScreenDataSource {

    suspend fun getLatestImages(): Resources<List<ImageList>> {
        val imageList = mutableListOf<ImageList>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("photo").get().await()
        for (images in querySnapshot.documents) {
            images.toObject(ImageList::class.java)?.let {
                imageList.add(it)
            }
        }
        return Resources.Success(imageList)
    }
}
