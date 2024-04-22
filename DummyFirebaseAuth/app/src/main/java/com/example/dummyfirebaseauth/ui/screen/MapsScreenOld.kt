package com.example.dummyfirebaseauth.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dummyfirebaseauth.presentation.MapsViewModel
import com.example.dummyfirebaseauth.tools.Util
import com.example.dummyfirebaseauth.tools.Util.Companion.getCurrentLocation
import com.example.dummyfirebaseauth.tools.Util.Companion.showToast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapsScreenOld(
    mapsViewModel: MapsViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var userLocation by remember {
        mutableStateOf<LatLng?>(null)
    }

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    LaunchedEffect(key1 = permissionState) {
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        if (allPermissionsRevoked) showToast(context, "all permission revoked")
        else if (permissionState.allPermissionsGranted)
            getCurrentLocation(
                onGetCurrentLocationFailed = {},
                onGetCurrentLocationSuccess = {
                    userLocation = LatLng(it.first, it.second)
                },
                priority = true,
                activity = activity
            )
        else showToast(context, "permission denied")
    }

    if (userLocation != null) {
        Scaffold {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                properties = mapsViewModel.state.properties,
                uiSettings = MapUiSettings(),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(userLocation!!, 15f)
                }
            )
        }
    }
}