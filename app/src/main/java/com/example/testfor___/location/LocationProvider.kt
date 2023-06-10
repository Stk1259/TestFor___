package com.example.testfor___.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class LocationProvider(private val context: Context) {
    @SuppressLint("MissingPermission")
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun getCityName(callback: (String?) -> Unit): String {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return "Location permission not granted"
        }

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val lastLocation: Location = locationResult.lastLocation

                val geocoder = Geocoder(context)
                val addresses = geocoder.getFromLocation(
                    lastLocation.latitude,
                    lastLocation.longitude,
                    1
                )

                if (!addresses.isNullOrEmpty()) {
                    val cityName = addresses[0].locality
                    println("User's city: $cityName")
                    callback.invoke(cityName)
                } else {
                    println("Unable to determine location")
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        return "Определение локации"
    }
}