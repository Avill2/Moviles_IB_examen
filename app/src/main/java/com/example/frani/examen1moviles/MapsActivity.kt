package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var autos:ArrayList<Autoc>
    var idConductor:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        idConductor = intent.getIntExtra("idConductor",0 )
        autos= DataBaseAuto.getAutosList(idConductor)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val zoom = 17f
        // Add a marker in Sydney and move the camera
        autos.forEach{ libro: Autoc ->
            val temp = LatLng(libro.latitud, libro.longitud)
            mMap.addMarker(MarkerOptions().position(temp).title(libro.nombre))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp, zoom))
        }
    }
}
