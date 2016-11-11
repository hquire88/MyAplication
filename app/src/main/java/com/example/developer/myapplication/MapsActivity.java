package com.example.developer.myapplication;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private List<BELWeaponItem> listWeapon = new ArrayList<>();

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
            int orange = Color.rgb(255, 102, 04);
            int lightOrange = Color.argb(50, 255, 178, 127);

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
                    .fillColor(lightOrange)
                    .strokeColor(orange));
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
        Toast.makeText(MapsActivity.this, "Enemigo localizado!",
                Toast.LENGTH_SHORT).show();
    }
}
