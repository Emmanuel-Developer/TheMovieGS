package com.prueba.themovieGS.ui.location

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.prueba.themovieGS.R
import com.prueba.themovieGS.databinding.FragmentLocationUserBinding
import com.prueba.themovieGS.manager.Constants.CHANNEL_ID
import com.prueba.themovieGS.manager.Constants.COLLECTION_ADDRESS_KEY
import com.prueba.themovieGS.manager.Constants.COLLECTION_LOCATION_KEY
import com.prueba.themovieGS.manager.Constants.REQUEST_CODE_LOCATION
import com.prueba.themovieGS.manager.Constants.SPACE
import com.prueba.themovieGS.ui.App
import io.grpc.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MapFragment : Fragment(R.layout.fragment_location_user) {

    private lateinit var binding: FragmentLocationUserBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val db = FirebaseFirestore.getInstance()
    private var handler: Handler = Handler()

    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationUserBinding.bind(view)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.btnGetLocation.setOnClickListener {
            getLocation()
        }

        binding.btnViewLocation.setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_locationUserFragment_to_locationMapsFragment)
        }
        runLocationSend()

        notificationManager = binding.root.context.getSystemService(NOTIFICATION_SERVICE) as
                NotificationManager
    }

    private fun runLocationSend() {
        handler.postDelayed(
            {
                CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        getLocation()
                    }
                }
            },
            5000
        )
    }

    private fun sentNotification() {
        val intent = Intent(binding.root.context, App::class.java)
        val pendingIntent = PendingIntent.getActivity(
            binding.root.context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(CHANNEL_ID, getText(R.string.save_location), NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(binding.root.context, CHANNEL_ID).setContentTitle(
                getText(R.string.app_name),
            ).setContentText(getText(R.string.save_location)).setSmallIcon(R.drawable.bg_map)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources, R.drawable
                            .post_home
                    )
                ).setContentIntent(pendingIntent)
        }
        notificationManager.notify(12345, builder.build())
    }

    private fun getLocation() {
        val lastLocation = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
            return
        }
        lastLocation.addOnSuccessListener {
            if (it != null) {
                binding.txtLat.text = it.latitude.toString()
                binding.txtLng.text = it.longitude.toString()

                db.collection(COLLECTION_ADDRESS_KEY).document(COLLECTION_LOCATION_KEY)
                    .set(GPS(it.latitude.toString(), it.longitude.toString()))
                    .addOnSuccessListener {
                        sentNotification()
                        Toast.makeText(
                            requireContext(),
                            getText(R.string.save_location),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "Firebase",
                            "Error writing document",
                            e
                        )
                    }
            }
        }
    }

    data class GPS(val latitude: String = SPACE, val longitude: String = SPACE)
}
