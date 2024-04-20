package com.example.dummyfirebaseauth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dummyfirebaseauth.presentation.maps.MapsState

class MapsViewModel : ViewModel() {
    var state by mutableStateOf(MapsState())
}