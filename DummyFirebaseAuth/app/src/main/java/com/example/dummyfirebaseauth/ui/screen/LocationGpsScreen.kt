package com.example.dummyfirebaseauth.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dummyfirebaseauth.presentation.location_gps.LocationService

@Composable
fun LocationGpsScreen() {
    val context = LocalContext.current
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
        0
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_START
//                startService(this)
            }
        }) {
            Text(text = "Start")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            Intent(context, LocationService::class.java).apply {
                action = LocationService.ACTION_STOP
//                startService(this)
            }
        }) {
            Text(text = "Stop")
        }
    }
}