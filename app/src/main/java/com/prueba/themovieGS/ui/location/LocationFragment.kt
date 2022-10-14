package com.prueba.themovieGS.ui.location

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.prueba.themovieGS.R
import com.prueba.themovieGS.databinding.FragmentLocationMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.prueba.themovieGS.manager.Constants.COLLECTION_ADDRESS_KEY
import com.prueba.themovieGS.manager.Constants.COLLECTION_LATITUDE_KEY
import com.prueba.themovieGS.manager.Constants.COLLECTION_LOCATION_KEY
import com.prueba.themovieGS.manager.Constants.COLLECTION_LONGITUDE_KEY
import com.prueba.themovieGS.manager.Constants.ERROR
import com.prueba.themovieGS.manager.Constants.ZOOM_10

class LocationFragment : Fragment(R.layout.fragment_location_maps) {

    private lateinit var binding: FragmentLocationMapsBinding
    private val db = FirebaseFirestore.getInstance()
    private val callback = OnMapReadyCallback { googleMap ->

        db.collection(COLLECTION_ADDRESS_KEY).document(COLLECTION_LOCATION_KEY).get()
            .addOnSuccessListener { document ->
                document?.let {
                    val lat = document.getString(COLLECTION_LATITUDE_KEY)?.toDouble()
                    val long = document.getString(COLLECTION_LONGITUDE_KEY)?.toDouble()
                    val location = LatLng(lat ?: 0.0, long ?: 0.0)
                    googleMap.addMarker(MarkerOptions().position(location).title("$lat $long"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_10))
                }
            }.addOnFailureListener { error ->
            Log.e(ERROR, error.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationMapsBinding.bind(view)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
