package com.example.dummyfirebaseauth.ui.screen

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.dummyfirebaseauth.tools.Util.Companion.getCurrentLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TrackUserLocationScreen (
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit
) {
    val activity = LocalContext.current as Activity
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    // Initialize the state for managing multiple location permissions.
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    // Use LaunchedEffect to handle permissions logic when the composition is launched.
    LaunchedEffect(key1 = permissionState) {
        Log.d("TULScreen", "Launched")
        // Check if all previously granted permissions are revoked.
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        // Filter permissions that need to be requested.
        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        // If there are permissions to request, launch the permission request.
        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        // Execute callbacks based on permission status.
        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        }

        else if (permissionState.allPermissionsGranted) {
            onPermissionGranted()
            getCurrentLocation(
                onGetCurrentLocationFailed = {},
                onGetCurrentLocationSuccess = {},
                priority = true,
                activity = activity
            )
        }

        else {
            onPermissionDenied()
        }
    }
}