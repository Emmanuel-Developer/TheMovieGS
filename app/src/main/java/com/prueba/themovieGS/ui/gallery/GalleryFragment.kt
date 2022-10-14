package com.prueba.themovieGS.ui.gallery

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.prueba.themovieGS.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.prueba.themovieGS.databinding.FragmentGalleryBinding
import com.prueba.themovieGS.manager.Constants.COLLECTION_IMAGE_URL_KEY
import com.prueba.themovieGS.manager.Constants.COLLECTION_NEW_IMAGE_KEY
import com.prueba.themovieGS.manager.Constants.COLLECTION_PHOTO_KEY
import com.prueba.themovieGS.manager.Constants.DATA_EXTRA
import com.prueba.themovieGS.manager.Constants.EXTENSION_IMAGE
import com.prueba.themovieGS.manager.Constants.REQUEST_IMAGE_CAPTURE
import java.io.ByteArrayOutputStream
import java.util.UUID

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private lateinit var binding: FragmentGalleryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)

        binding.btnTakePicture.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.btnViewPhotos.setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_photoUserFragment_to_photoListFragment)
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext().applicationContext,
                "No se encontro app para tomar la foto",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun uploadPicture(bitmap: Bitmap) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef =
            storageRef.child("$COLLECTION_PHOTO_KEY/${UUID.randomUUID()}$EXTENSION_IMAGE")
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()

        imagesRef.putBytes(data).continueWithTask {
            if (!it.isSuccessful) {
                it.exception?.let { exception -> throw exception }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                val downloadUrl = it.result.toString()
                FirebaseFirestore.getInstance().collection(COLLECTION_PHOTO_KEY)
                    .document(COLLECTION_NEW_IMAGE_KEY).update(
                    mapOf(COLLECTION_IMAGE_URL_KEY to downloadUrl)
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get(DATA_EXTRA) as Bitmap
            binding.ivPhotoUser.setImageBitmap(imageBitmap)
            uploadPicture(imageBitmap)
        }
    }
}
