package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.WaterReport;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final Facade facade = Facade.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * Adds markers at every location where there is a water report
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<WaterReport> reportList = facade.getReports();
        for (WaterReport r : reportList) {
            LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(loc).title(r.getLocation()).snippet(r.getSnippet()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    /**
     * Sets what the marker looks like when it is clicked
     */
    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View myContentsView;

        CustomInfoWindowAdapter() {
            myContentsView = getLayoutInflater().inflate(R.layout.map_marker,
                    (ViewGroup) findViewById(R.id.map), false);
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
            tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
    }
}

