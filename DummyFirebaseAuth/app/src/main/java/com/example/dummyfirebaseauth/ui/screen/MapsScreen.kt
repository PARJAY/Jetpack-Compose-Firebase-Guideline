package com.example.dummyfirebaseauth.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dummyfirebaseauth.presentation.MapsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(
    mapsViewModel: MapsViewModel = viewModel()
) {

    val bluelakeLocation = LatLng(-8.68227956680623,  115.22257486207766)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bluelakeLocation, 15f)
    }

    // Request user location permission (if needed)

    Scaffold (
        floatingActionButton = {
//            FloatingActionButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Default.LocationOn,
//                    contentDescription = "Toggle",
//                )
//            }
        }
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            properties = mapsViewModel.state.properties,
            uiSettings = MapUiSettings(),
            cameraPositionState = cameraPositionState
        )
    }
}