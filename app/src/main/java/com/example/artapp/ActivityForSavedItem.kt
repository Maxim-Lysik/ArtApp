package com.example.artapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_for_saved_item.*

class ActivityForSavedItem : AppCompatActivity(), LifecycleObserver, OnMapReadyCallback {


    private lateinit var mMapView: MapView
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_saved_item)


        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()


        val picture = intent.getStringExtra("Pic_link")
        val geolat = intent.getStringExtra("Geo_lat")
        val geolon = intent.getStringExtra("Geo_lon")
        val address = intent.getStringExtra("Address")
        val artist = intent.getStringExtra("Artist")
        val streetlink = intent.getStringExtra("Street_link")
        val year = intent.getStringExtra("Year")
        val city = intent.getStringExtra("City")

        val our_artist: TextView = findViewById(R.id.artists) as TextView
        val our_address: TextView = findViewById(R.id.art_address) as TextView
        val our_year: TextView = findViewById(R.id.created_year) as TextView
        val our_imageview: ImageView = findViewById(R.id.our_image)
        val route: Button = findViewById(R.id.route)
        val share: Button = findViewById(R.id.share)



        mMapView = findViewById(R.id.mapView)
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync(this)

        our_artist.setText(artist)
        our_address.setText(address)
        our_year.setText(year)
        Glide
            .with(this)
            .load(picture)
            .placeholder(R.drawable.progress_animation)
            .into(our_imageview)


        val geo_lat_mark = geolat.toString()
        val geo_lon_mark = geolon.toString()

        var our_uri =
            "http://maps.google.com/maps?daddr=$geo_lat_mark,$geo_lon_mark"

        route.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(our_uri)
            )
            startActivity(intent)

        }


        share.setOnClickListener {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "Hi! Check this piece of art: " +
                            "${address} (${geo_lat_mark} , ${geo_lon_mark})"
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }



        textView12.setOnClickListener {

            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.streetview:cbll=${geo_lat_mark},${geo_lon_mark}")
                )
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent)
            } catch (e: Exception) {

                val text = "Sorry! Streetview can not be found now"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

        }




        floating_button_saved.setOnClickListener {

            finish()

        }


    }

    override fun onMapReady(googleMap: GoogleMap) {


        map = googleMap
        mMapView = findViewById(R.id.mapView);

        map.setOnMapClickListener { Log.i("onMapClick", "Horray!") }
        map.getUiSettings().setMapToolbarEnabled(false)
        map.getUiSettings().setAllGesturesEnabled(false)

        val geolat2 = intent.getStringExtra("Geo_lat")
        val geolon2 = intent.getStringExtra("Geo_lon")

        val geo_lat_mark2 = geolat2.toString().toDouble()
        val geo_lon_mark2 = geolon2.toString().toDouble()

        map.addMarker(
            MarkerOptions().position(LatLng(geo_lat_mark2, geo_lon_mark2))
                .title("Marker")
        )
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    geo_lat_mark2,
                    geo_lon_mark2
                ), 15f
            )
        )

    }


    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }


    override fun onPause() {
        mMapView!!.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMapView!!.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }


}