package com.example.placeofattractions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.placeofattractions.databinding.ActivityMapBinding
import org.osmdroid.views.MapView
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapActivity : AppCompatActivity() {

    private lateinit var myMap: MapView
    private lateinit var myMapController: IMapController

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(applicationContext, this.getPreferences(MODE_PRIVATE))

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = application as MyApplication

        myMap = binding.map

        myMap.setTileSource(TileSourceFactory.MAPNIK)
        myMap.setMultiTouchControls(true)
        myMapController = myMap.controller
        myMapController.setZoom(9.0)
        val startPointMap = GeoPoint(46.554649, 15.645881)
        myMapController.setCenter(startPointMap)

        for (i in 0 until app.data.size)
        {
            val marker = Marker(myMap)
            marker.position = GeoPoint(app.data[i].latitude, app.data[i].longitude)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.icon = ContextCompat.getDrawable(this, R.drawable.locationicon)
            marker.title = "Attraction Name: ${app.data[i].name}"
            myMap.overlays.add(marker)
            myMap.invalidate()
        }
    }
}