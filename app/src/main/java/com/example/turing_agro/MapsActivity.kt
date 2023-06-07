package com.example.turing_agro

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.turing_agro.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMapClickListener, OnMarkerDragListener, OnMarkerClickListener {

    private var editable: Boolean = false
    private lateinit var mMap: GoogleMap
    private lateinit var mPolygon: Polygon
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mPolygonJoints: ArrayList<LatLng> = ArrayList()
    private var mPolygonMarkers: ArrayList<Marker> = ArrayList()
    private lateinit var mDelButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val addButton = findViewById<FloatingActionButton>(R.id.add_floating_button)
        addButton.setOnClickListener{
            editable = if (editable){
                Toast.makeText(this, "Desligando edição", Toast.LENGTH_SHORT).show()
                false
            } else{
                Toast.makeText(this, "Iniciando edição", Toast.LENGTH_SHORT).show()
                true
            }
        }
        mDelButton = findViewById(R.id.del_floating_button)
        mDelButton.setOnClickListener{
            if (mPolygonJoints.isNotEmpty()){
                for (marker in mPolygonMarkers){
                    marker.remove()
                }
                mPolygonMarkers.clear()
                mPolygonJoints.clear()
                mDelButton.visibility = View.GONE
                mPolygon.remove()
            }
        }

        val sendButton = findViewById<FloatingActionButton>(R.id.send_floating_button)
        sendButton.setOnClickListener{
            if(mPolygonJoints.size > 3){
                if (checkIfIntersectes(mPolygonJoints)){
                    Toast.makeText(this, "A sua área está irregular, por favor corrija-a!", Toast.LENGTH_LONG).show()
                } else{
                    val generatingIntent = Intent(this, GeneratingActivity::class.java)
                    generatingIntent.putExtra("points",mPolygonJoints as Serializable)
                    startActivity(generatingIntent)
                }

            } else{
                Toast.makeText(this, "Por Favor, defina o local de sua terra com ao menos 4 pontos!", Toast.LENGTH_LONG).show()
            }
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        // set to current location
        //mMap.setOnPolygonClickListener(this)
        mMap.setOnMapClickListener(this)
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMarkerDragListener(this)
        setLocation()
    }

    @SuppressLint("MissingPermission")
    private fun setLocation() {
        mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
            val location: Location? = task.result
            if (location != null) {
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location.latitude,
                            location.longitude
                        ), 17f
                    )
                )
                val poligonOptions = PolygonOptions()
                    .strokeWidth(15F)
                    .add(LatLng(location.latitude, location.longitude))
                mPolygon = mMap.addPolygon(poligonOptions)
            }
        }
    }

    override fun onMapClick(clickLocation: LatLng) {
        if (editable){
            mPolygonJoints.add(clickLocation)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(clickLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.turing_agro_map_icon))
                    .anchor(0.5f, 0.5f)
                    .draggable(true)
            )
            if (marker != null) {
                mPolygonMarkers.add(marker)
            }
            mDelButton.visibility = View.VISIBLE
            drawPolygon()
        }
    }

    private fun drawPolygon() {
        if (mPolygonJoints.isNotEmpty()){
            mPolygon.remove()
            val poligonOptions = PolygonOptions()
                .strokeJointType(JointType.ROUND)
                .strokeColor(resources.getColor(R.color.md_theme_light_secondary))
                .strokeWidth(15F)
                .addAll(mPolygonJoints)
            mPolygon = mMap.addPolygon(poligonOptions)
        }
    }

    override fun onMarkerDrag(p0: Marker) {
        val newPosition = LatLng(p0.position.latitude, p0.position.longitude)
        val index = mPolygonMarkers.indexOf(p0)
        mPolygonJoints[index] = newPosition
        drawPolygon()
    }

    override fun onMarkerDragEnd(p0: Marker) {
        return
    }

    override fun onMarkerDragStart(p0: Marker) {
        return
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        val index = mPolygonMarkers.indexOf(p0)
        p0.remove()
        mPolygonJoints.removeAt(index)
        mPolygonMarkers.removeAt(index)
        drawPolygon()
        if (mPolygonJoints.isEmpty()){
            mDelButton.visibility = View.GONE
        }
        return true
    }

    private fun intersectionBetweenSegmentsCL(p0: LatLng, p1: LatLng, p2: LatLng, p3: LatLng): Boolean {
        var denominator = (p3.longitude - p2.longitude) * (p1.latitude - p0.latitude) - (p3.latitude - p2.latitude) * (p1.longitude - p0.longitude)
        var ua = (p3.latitude - p2.latitude) * (p0.longitude - p2.longitude) - (p3.longitude - p2.longitude) * (p0.latitude - p2.latitude)
        var ub = (p1.latitude - p0.latitude) * (p0.longitude - p2.longitude) - (p1.longitude - p0.longitude) * (p0.latitude - p2.latitude)

        if (denominator < 0) {
            ua = -ua; ub = -ub
            denominator = -denominator
        }

        if (ua >= 0.0 && ua <= denominator && ub >= 0.0 && ub <= denominator && !denominator.equals(0.0)) {
            return true
        }
        return false
    }

    private fun checkIfIntersectes(list: ArrayList<LatLng>): Boolean{
        if (list.size > 2) {
            val size = list.size - 1

            for (i in 1 until  size) {
                for (j in 0 until i-1) {
                    if (intersectionBetweenSegmentsCL(list[i], list[i+1], list[j], list[j+1])) {
                        return true
                    }
                }
            }
        }
        return false
    }

}
