package com.example.dummyfirebaseauth.tools

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.dummyfirebaseauth.ui.screen.fusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class Util {
    companion object {
        @SuppressLint("MissingPermission")
        fun getCurrentLocation(
            onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
            onGetCurrentLocationFailed: (Exception) -> Unit,
            priority: Boolean = true,
            activity : Activity
        ) {
            // Determine the accuracy priority based on the 'priority' parameter
            val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
            else Priority.PRIORITY_BALANCED_POWER_ACCURACY

            // Check if location permissions are granted
            if (areLocationPermissionsGranted(activity)) {
                // Retrieve the current location asynchronously
                fusedLocationProviderClient.getCurrentLocation(
                    accuracy, CancellationTokenSource().token,
                ).addOnSuccessListener { location ->
                    location?.let {
                        // If location is not null, invoke the success callback with latitude and longitude
                        onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
                        Log.d("Util", "Success Location : ${Pair(it.latitude, it.longitude)}")
                    }
                }.addOnFailureListener { exception ->
                    // If an error occurs, invoke the failure callback with the exception
                    onGetCurrentLocationFailed(exception)
                    Log.d("Util", "Error exception : $exception")
                }
            }
        }

        private fun areLocationPermissionsGranted(activity : Activity): Boolean {
            return (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        activity, Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED)
        }

        fun showToast(context : Context, text : String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
}