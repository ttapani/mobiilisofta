package asia.jokin.ohjelmistomobiili

import android.content.res.TypedArray
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class RouteResultsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var mapView: SupportMapFragment
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.routes_results_activity)
        viewManager = LinearLayoutManager(this)

        mapView = supportFragmentManager.findFragmentById(R.id.routeInfoMap) as SupportMapFragment
        mapView.getMapAsync(this)

        val data = intent.getParcelableArrayListExtra<Route>("resultsData")

        viewAdapter = RoutesResultsCardAdapter(data, this)
        recyclerView = findViewById<RecyclerView>(R.id.routeResultsRecyclerVIew).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(false)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val lat = intent.getDoubleExtra("lat", LocationSingleton.getLat())
        val lng = intent.getDoubleExtra("lng", LocationSingleton.getLng())

        val curLatLng = LatLng(lat, lng)

        // Kartta itse
        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(LocationSingleton.getLat(), LocationSingleton.getLng()), 16.0F))
        mMap.setOnCameraIdleListener(this)
        mMap.setOnMarkerClickListener(this)
    }

    override fun onCameraIdle() {
        // Minne kartta osoittaa
        val curLatLng = mMap.getCameraPosition().target

        // Tutkintaa onko kartta siirtynyt tarpeeksi?
    }

    override fun onMarkerClick(mMarker: Marker): Boolean {

        return true
    }
}