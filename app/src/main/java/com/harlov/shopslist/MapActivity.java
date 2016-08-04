package com.harlov.shopslist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.TypedValue;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_FINE_LOCATION = 121;
    private static final int REQUEST_COARSE_LOCATION = 171;
    private MapFragment mapFragment;
    private LatLngBounds.Builder builder = new LatLngBounds.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    Toast.makeText(this, R.string.need_fine_location_permission_toast, Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    Toast.makeText(this, R.string.need_coarse_location_permission_toast, Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
            }
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        setupMarkers(googleMap, ShopList.shopsArrayList);

        LatLngBounds bounds = builder.build();
        Resources resources = getResources();

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                resources.getDisplayMetrics());
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                googleMap.moveCamera(cameraUpdate);
                // Remove listener to prevent position reset on camera move.
                googleMap.setOnCameraChangeListener(null);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_FINE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, R.string.permission_not_granted_toast, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_COARSE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, R.string.permission_not_granted_toast, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setupMarkers(GoogleMap googleMap, ArrayList<Shop> shopsArrayList){
        for (int i = 0; i < shopsArrayList.size(); i++){
            Shop shop = shopsArrayList.get(i);
            LatLng shopLatLng = new LatLng((double) shop.getLatitude() / 1000000,
                    (double) shop.getLongitude() / 1000000);

            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(shopLatLng).title(shop.getName()));

            builder.include(marker.getPosition());
        }
    }
}
