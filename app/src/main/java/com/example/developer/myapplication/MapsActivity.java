package com.example.developer.myapplication;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private Marker marker;
    private Location mLastLocation;
    private List<BELWeaponItem> listWeapon = new ArrayList<>();
    TextView myLocationLat;
    TextView myLocationLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void showEnemyPosition(GoogleMap googleMap)
    {
        for (int i= 0; i<listWeapon.size();i++)
        {
            BELWeaponItem item = listWeapon.get(i);

            mMap = googleMap;
            LatLng mapEnemyPosition = new LatLng(item.getLocation().getLatitude(),item.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.enemypositionicon))
                    .flat(true)
                    .position(mapEnemyPosition)
                    .rotation(0)
                    .title(item.getCode())
                    .snippet(item.getKind()));
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(item.getLocation().getLatitude(), item.getLocation().getLongitude()))
                    .radius(item.getRadiusInMeter())
                    .strokeColor(Color.RED));
        }
    }

    // Posicionamiento del mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        googleMap.setMyLocationEnabled(true);

        mMap = googleMap;
        LatLng mapCenter = new LatLng(-34.606556, -58.435528);

        this.fetchWeaponLocation(googleMap);

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(13)
                .bearing(0)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
    }

    // Mostrar Armas enemigas
    private void fetchWeaponLocation(GoogleMap googleMap)
    {

        WeaponParseActivity weaponParseActivity = (WeaponParseActivity) new WeaponParseActivity().execute();
        try {
            listWeapon = weaponParseActivity.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();        }

        this.showEnemyPosition(googleMap);
    }





}
