package com.example.artapp

//import RetroRepository

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, CoroutineScope {


    private val TAG = "ILogs"
    private var job: Job = Job()
    var markerList: ArrayList<Marker> = ArrayList()
    private val mapsViewModel: MapsViewModel by viewModels()
    private lateinit var map: GoogleMap
    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ConstraintLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val city = intent.getStringExtra(NAME)

        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheet)

        val city_name: TextView = findViewById(R.id.city_text)
        val artists_found: TextView = findViewById(R.id.tvTitle)
        val artworks_nearby: TextView = findViewById(R.id.tvSubtitle)
        val button_saved: Button = findViewById(R.id.saved_button)
        val number_of_artists: TextView = findViewById(R.id.number1)
        val number_of_artworks: TextView = findViewById(R.id.number2)


        bottomSheetBehaviour.apply {

            peekHeight = 138
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            this.state = BottomSheetBehavior.STATE_HIDDEN

            val city = intent.getStringExtra(NAME)
            city_name.setText(city.toString())
            artists_found.setText("Artists found ")
            artworks_nearby.setText("Artworks nearby ")
            number_of_artists.setText("${city?.let { numberOfArtists(it) }}")
            number_of_artworks.setText("${city?.let { numberOfArtworks(it) }}")


            button_saved.setOnClickListener {

                val intent_saved =
                    Intent(this@MapsActivity, ActivityForSaved::class.java)
                startActivity(intent_saved)

            }

        }


        val city_second_usage = city.toString()

        mapsViewModel.itemsByCity(city_second_usage).observe(this, Observer { artitems ->

            for (ArtItem in artitems) {

                //Setting "NO IMAGE FOUND" picture, if pictLink leads to old/ covered art

                if (ArtItem.pictLink == "https://street-art.s3.amazonaws.com/v1/b18b96a8-26ed-4f52-87cc-075ab246e42c/4f858d09-c9ce-45b6-a1ac-a11d7bb0e64f.jpg") {
                    ArtItem.pictLink =
                        "https://i.ibb.co/XYNfnCR/imgonline-com-ua-dexif-J11-TBi-KNXOTO.jpg"
                    mapsViewModel.updateitem(ArtItem)

                }

                val Our_latitude = ArtItem.geoCoordinates.lat.toDouble()
                val Our_lontitude = ArtItem.geoCoordinates.lon.toDouble()
                val Our_latLng = LatLng(Our_latitude, Our_lontitude)

                val Our_marker = map.addMarker(
                    MarkerOptions()
                        .position(Our_latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker3)))

                Our_marker.setTag(ArtItem.geoCoordinates);
                markerList.add(Our_marker)

            }

        })


    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()
        var job: Job? = null

        map.setOnMarkerClickListener { marker ->
            var marker_position_lat = marker.position.latitude.toString()
            var marker_position_lon = marker.position.longitude.toString()

            val intent =
                Intent(this@MapsActivity, Activity_for_mark_data::class.java)

            intent.putExtra("Geo1", marker_position_lat)
            intent.putExtra("Geo2", marker_position_lon)

            startActivity(intent)

            true
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationAccess()
            } else {
                Toast.makeText(
                    this,
                    "User has not granted location access permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }


    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 500
        locationRequest.fastestInterval = 100
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        val city_for_map = intent.getStringExtra(NAME)
        val location_to_put = city_for_map?.let { animating_camera(it) }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location_to_put, 13f))


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        val markerOptions = MarkerOptions().position(latLng)
                    }
                }
            }
        }
    }


    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            getLocationUpdates()
            startLocationUpdates()
        } else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
    }


    companion object {
        const val NAME = "NAME"
    }


    private fun animating_camera(city_for_map: String): LatLng {

        var latLng = LatLng(60.192059, 24.945831) // Helsinki as a start

        val location_to_animate = when (city_for_map) {

            // Germany
            "Berlin" -> LatLng(52.520007, 13.404954)
            "Aachen" -> LatLng(50.775555, 6.083611)
            "Essen" -> LatLng(51.450832, 7.013056)
            "Mannheim" -> LatLng(49.4874592, 8.4660395)
            "Dortmund" -> LatLng(51.513587, 7.465298)

            //France
            "Montpellier" -> LatLng(43.611900, 3.877200)
            "Strasbourg" -> LatLng(48.580002, 7.750000)
            "Bordeaux" -> LatLng(44.84044, -0.5805)
            "Toulouse" -> LatLng(43.604500, 1.444000)

            //Belgium
            "Gent" -> LatLng(51.049999, 3.733333)
            "Oostende" -> LatLng(51.228443, 2.934465)
            "Antwerpen" -> LatLng(51.2194475, 4.4024643)
            "Leuven" -> LatLng(50.883333, 4.700000)

            //Norway
            "Stavanger" -> LatLng(58.9699756, 5.7331073)
            "Oslo" -> LatLng(59.911491, 10.757933)
            "Bergen" -> LatLng(60.397076, 5.324383)

            //Spain
            "Valencia" -> LatLng(39.4699075, -0.3762881)
            "Vigo" -> LatLng(42.231358, -8.712447)

            //Netherlands
            "Leeuwarden" -> LatLng(53.2012334, 5.7999133)
            "Amsterdam" -> LatLng(52.370216, 4.895168)
            "Arnhem" -> LatLng(51.9851034, 5.8987296)
            "Rotterdam" -> LatLng(51.9244201, 4.4777325)
            "Eindhoven" -> LatLng(51.441642, 5.4697225)

            //Estonia
            "Tallinn" -> LatLng(59.436962, 24.753574)

            //Italy
            "Padova" -> LatLng(45.406435, 11.876761)
            "Milano" -> LatLng(45.464664, 9.188540)
            else -> {
                LatLng(60.192059, 24.945831)
            }
        }
        return location_to_animate
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    private fun numberOfArtists(city_for_map: String): Int {

        var counter: Int = 0

        counter = when (city_for_map) {

            // Germany
            "Berlin" -> 64
            "Aachen" -> 19
            "Essen" -> 34
            "Mannheim" -> 10
            "Dortmund" -> 6

            //France
            "Montpellier" -> 53
            "Strasbourg" -> 27
            "Bordeaux" -> 21
            "Toulouse" -> 29

            //Belgium
            "Gent" -> 61
            "Oostende" -> 17
            "Antwerpen" -> 38
            "Leuven" -> 18

            //Norway
            "Stavanger" -> 28
            "Oslo" -> 9
            "Bergen" -> 21

            //Spain
            "Valencia" -> 25
            "Vigo" -> 30

            //Netherlands
            "Leeuwarden" -> 13
            "Amsterdam" -> 30
            "Arnhem" -> 15
            "Rotterdam" -> 26
            "Eindhoven" -> 27

            //Estonia
            "Tallinn" -> 10

            //Italy
            "Padova" -> 9
            "Milano" -> 11
            else -> {
                1
            }
        }
        return counter

    }


    private fun numberOfArtworks(city_for_map: String): Int {

        var counter: Int = 0

        counter = when (city_for_map) {

            // Germany
            "Berlin" -> 46
            "Aachen" -> 27
            "Essen" -> 38
            "Mannheim" -> 10
            "Dortmund" -> 6

            //France
            "Montpellier" -> 56
            "Strasbourg" -> 47
            "Bordeaux" -> 15
            "Toulouse" -> 16

            //Belgium
            "Gent" -> 55
            "Oostende" -> 19
            "Antwerpen" -> 24
            "Leuven" -> 12

            //Norway
            "Stavanger" -> 29
            "Oslo" -> 10
            "Bergen" -> 25

            //Spain
            "Valencia" -> 18
            "Vigo" -> 31

            //Netherlands
            "Leeuwarden" -> 13
            "Amsterdam" -> 28
            "Arnhem" -> 14
            "Rotterdam" -> 14
            "Eindhoven" -> 14

            //Estonia
            "Tallinn" -> 11

            //Italy
            "Padova" -> 19
            "Milano" -> 11
            else -> {
                1
            }
        }
        return counter

    }


}