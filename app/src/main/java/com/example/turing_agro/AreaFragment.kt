package com.example.turing_agro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.turing_agro.models.Polygon
import com.example.turing_agro.models.Vertice
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions

class AreaFragment : Fragment() {

    private lateinit var centroid: LatLng
    private lateinit var poligons: ArrayList<Polygon>
    private lateinit var centers: ArrayList<Vertice>
    val colors = arrayOf(R.color.red, R.color.blue, R.color.green, R.color.yellow)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = requireActivity().intent
        if (intent == null) {
            Log.v("LOG_TAG", "Intent is null")
            return
        }
        val bundle = intent.extras
        if (bundle == null) {
            Log.v("LOG_TAG", "bundle is: NULL")
            return
        }
        val latitude  = bundle.getDouble("latitude")
        val longitude  = bundle.getDouble("longitude")
        centroid = LatLng(latitude, longitude)
        poligons = bundle.getSerializable("poligons") as ArrayList<Polygon>
        centers = bundle.getSerializable("centers") as ArrayList<Vertice>
        Log.d("TESTE", "onCreate: $centers")
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centroid, 15f))
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        for (i in 0 until poligons.size){
            googleMap.addPolygon(PolygonOptions()
                .strokeJointType(JointType.ROUND)
                .strokeColor(resources.getColor(colors[i]))
                .fillColor(resources.getColor(colors[i]))
                .strokeWidth(5F)
                .addAll(poligons[i].toLatLngList()))
            val area = i + 1
            googleMap.addMarker(
                MarkerOptions()
                    .position(centers[i].toLatLng())
                    .title("Área $area")
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_area, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}