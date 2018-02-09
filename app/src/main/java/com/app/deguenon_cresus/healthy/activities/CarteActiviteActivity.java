package com.app.deguenon_cresus.healthy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.deguenon_cresus.healthy.models.Position;
import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.adapters.CaracteristiquesExerciceAdapter;
import com.app.deguenon_cresus.healthy.models.CarateristiqueExercice;
import com.app.deguenon_cresus.healthy.models.Exercice;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 17/11/2017.
 */

public class CarteActiviteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSION_GPS = 100;
    private LocationManager lm;

    private static final int LOCATION_INTERVAL = 0;
    private static final float LOCATION_DISTANCE = 0f;

    private GoogleMap mMap;

    private CaracteristiquesExerciceAdapter adapter;

    private ArrayList<CarateristiqueExercice> carateristiqueExerciceArrayList;

    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    private Exercice monExercice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent.getSerializableExtra("Exercice") == null) {

            startActivity(new Intent(this, MesActivitesJournalieresActivity.class));
            return;
        }

        setContentView(R.layout.activity_carte_jour);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Initialisation de l'Exercice
        monExercice = new Exercice();
        monExercice = (Exercice) intent.getSerializableExtra("Exercice");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(" " + monExercice.getTypeExercice().getNom());
        getSupportActionBar().setIcon(monExercice.getTypeExercice().getIcon());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.ic_back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarteActiviteActivity.this, MesActivitesJournalieresActivity.class));
                finish();
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(49.43260413376946, 1.0813751048516318);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Yoooo what are you doing ?"));
        mMap.setMinZoomPreference(12);
        /*mMap.setMaxZoomPreference(12);*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        afficherParcours();
    }

    public void afficherParcours() {
        ArrayList<Position> positions = monExercice.getPositions();

        if(positions == null) positions = new ArrayList<Position>();

        PolylineOptions lines = new PolylineOptions();

        MarkerOptions mark = new MarkerOptions();

        mMap.clear();

        for (int i = 0; i < positions.size(); i++) {
            lines.add(new LatLng(positions.get(i).getLat(), positions.get(i).getLng()));
        }

        mMap.addPolyline(lines
                .width(10)
                .color(Color.BLUE));

        if (positions.size() > 0) {
            LatLng initLatLng = new LatLng(positions.get(0).getLat(), positions.get(0).getLng());
            LatLng lastLatLng = new LatLng(positions.get(positions.size() - 1).getLat(), positions.get(positions.size() - 1).getLng());

            mMap.addMarker(new MarkerOptions().position(initLatLng).title(getResources().getString(R.string.depart)));
            mMap.addMarker(new MarkerOptions().position(lastLatLng).title(getResources().getString(R.string.finale)));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLatLng));
        }

    }
}
