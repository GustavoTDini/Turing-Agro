package com.example.turing_agro.models

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

class Polygon(
    val vertices: List<Vertice>,
) : Serializable {
    override fun toString(): String {
        return "Polygon(vertices=$vertices)"
    }

    fun toLatLngList(): List<LatLng>{
        val list = ArrayList<LatLng>()
        for (i in vertices){
            list.add(i.toLatLng())
        }
        return list
    }
}