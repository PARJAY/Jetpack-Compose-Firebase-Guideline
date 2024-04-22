package com.example.dummyfirebaseauth.presentation.maps

import com.google.android.gms.maps.model.LatLng

sealed class LocationState {
    object NoPermission: LocationState()
    object LocationDisabled: LocationState()
    object LocationLoading: LocationState()
    data class LocationAvailable(val location: LatLng): LocationState()
    object Error: LocationState()
}