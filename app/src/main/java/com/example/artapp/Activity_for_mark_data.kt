package com.example.artapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.artapp.data.response.SavedItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_for_mark_data2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Activity_for_mark_data : AppCompatActivity(), LifecycleObserver, OnMapReadyCallback {


    private lateinit var mMapView: MapView
    private lateinit var map: GoogleMap
    private val TAG = "ILogz"
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_mark_data2)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);


        val lat = intent.getStringExtra("Geo1")
        val lon = intent.getStringExtra("Geo2")
        val our_artist: TextView = findViewById(R.id.artists) as TextView
        val our_address: TextView = findViewById(R.id.art_address) as TextView
        val our_year: TextView = findViewById(R.id.created_year) as TextView
        val our_imageview: ImageView = findViewById(R.id.our_image)
        val our_button: Button = findViewById(R.id.route)
        val our_button2: Button = findViewById(R.id.share)
        val saved_view: ImageView = findViewById(R.id.save_view)



        mapsViewModel.our_artitems.observe(this, Observer { artitems ->
            artitems?.let {

                for (artitem in artitems) {
                    Log.i(TAG, "AITEM FROM LIVEDATA: $artitem")

                    val Our_latitude = artitem.geoCoordinates.lat.toString()
                    val Our_lontitude = artitem.geoCoordinates.lon.toString()

                    val geo_lat_mark = lat.toString()
                    val geo_lon_mark = lon.toString()


                    if (Our_latitude == geo_lat_mark && Our_lontitude == geo_lon_mark) {
                        Log.i(TAG, "OUR LATITUDE: $Our_latitude + OUR LONGTITUDE: $Our_lontitude")
                        our_artist.setText(artitem.writersNames)
                        our_address.setText(artitem.address)
                        our_year.setText(artitem.createdYear)


                        var our_uri =
                            "http://maps.google.com/maps?daddr=$geo_lat_mark,$geo_lon_mark"

                        Glide
                            .with(this)
                            .load(artitem.pictLink)
                            .placeholder(R.drawable.progress_animation)
                            .into(our_imageview)

                        our_button.setOnClickListener {

                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(our_uri)
                            )
                            startActivity(intent)

                        }




                        our_button2.setOnClickListener {

                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT, "Hi! Check this piece of art: " +
                                            "${artitem.address} (${artitem.geoCoordinates.lat} , ${artitem.geoCoordinates.lon})"
                                )
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(shareIntent)


                        }


                        saved_view.setOnClickListener {

                            val savedone = SavedItem(artitem.address,
                                artitem.cityName,
                                artitem.createdYear,
                                artitem.geoCoordinates,
                                artitem.guid,
                                artitem.linkOpenStreetMap,
                                artitem.pictLink,
                                artitem.writersNames)

                            CoroutineScope(Dispatchers.IO).launch {
                                mapsViewModel.insertSaveItem(savedone)
                            }

                            saved_view.setImageResource(R.drawable.icons_saved_ok);

                            val text = "Item saved!"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(this, text, duration)
                            toast.show()


                        }




                        textView12.setOnClickListener {


                            try {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("google.streetview:cbll=${artitem.geoCoordinates.lat.toString()},${artitem.geoCoordinates.lon.toString()}")           // STREETVIEW WORKS
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


                }


            }
        })


    }

    override fun onBackPressed() {
        finish()

        return
    }


    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        mMapView = findViewById(R.id.mapView);

        map.setOnMapClickListener { Log.i("onMapClick", "Horray!") }
        map.getUiSettings().setMapToolbarEnabled(false)
        map.getUiSettings().setAllGesturesEnabled(false)


        val lat = intent.getStringExtra("Geo1")
        val lon = intent.getStringExtra("Geo2")
        Log.i(TAG, "Latitude = $lat + longtitude = $lon")


        mapsViewModel.our_artitems.observe(this, Observer { artitems ->
            artitems?.let {

                for (artitem in artitems) {

                    val Our_latitude = artitem.geoCoordinates.lat.toString()
                    val Our_lontitude = artitem.geoCoordinates.lon.toString()

                    val geo_lat_mark = lat.toString()
                    val geo_lon_mark = lon.toString()


                    if (Our_latitude == geo_lat_mark && Our_lontitude == geo_lon_mark) {

                        val geo_lat_mark2 = lat.toString().toDouble()
                        val geo_lon_mark2 = lon.toString().toDouble()
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


                }


            }
        })


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