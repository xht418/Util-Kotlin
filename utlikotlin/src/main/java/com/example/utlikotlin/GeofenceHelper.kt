package com.example.utlikotlin

import android.annotation.SuppressLint
import android.app.PendingIntent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest

class GeofenceHelper(
    private val geofenceRadiusInMeters: Float,
    private val geofenceExpirationInMilliseconds: Long,
    private val landmarks: List<Landmark>,
    private val pendingIntent: PendingIntent,
    private val geofencingClient: GeofencingClient
) {
    init {
        addAllGeofences()
    }

    private fun addAllGeofences() = landmarks.forEach { addGeofence(it) }

    @SuppressLint("MissingPermission")
    private fun addGeofence(landmark: Landmark) {
        val geofence = Geofence.Builder().run {
            setRequestId(landmark.nameAsId)
            setCircularRegion(landmark.latLong.latitude, landmark.latLong.longitude, geofenceRadiusInMeters)
            setExpirationDuration(geofenceExpirationInMilliseconds)
            setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)      //can use or
            build()
        }

        val geofencingRequest = GeofencingRequest.Builder().run {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofence(geofence)
            build()
        }

        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
    }

    fun removeGeofences() = geofencingClient.removeGeofences(pendingIntent)
}