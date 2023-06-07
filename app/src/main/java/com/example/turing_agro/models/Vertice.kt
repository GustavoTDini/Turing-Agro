package com.example.turing_agro.models

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

class Vertice (
    val latitude: Double,
    val longitude: Double,
) : Serializable {
    override fun toString(): String {
        return "Vertice(latitude=$latitude, longitude=$longitude)"
    }

    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
}