package com.app.deguenon_cresus.healthy.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.deguenon_cresus.healthy.apps.AppConstantes;
import com.app.deguenon_cresus.healthy.apps.VariablesGlobales;
import com.app.deguenon_cresus.healthy.models.Position;
import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.models.Exercice;
import com.app.deguenon_cresus.healthy.models.TypeExercice;
import com.app.deguenon_cresus.healthy.models.Utilisateur;
import com.app.deguenon_cresus.healthy.sqlites.ExerciceDAO;
import com.app.deguenon_cresus.healthy.sqlites.PositionDAO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class CommencerExerciceActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private static final int PERMISSION_GPS = 100;
    private LocationManager lm;

    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 0f;
    private static final float DISTANCE_MIN = 10f;

    private GoogleMap mMap;

    private TextView vitesse;

    private TextView distance;

    private TextView time;

    private TextView calories;

    private ArrayList<Position> positionsForVitesse;

    private FloatingActionButton fab;
    private boolean isStart = false;
    private boolean isPause = false;
    private float heart_rate;

    private Exercice monExercice;

    long MillisecondTime, millis , StartTime, PauseTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, m_sec, MilliSeconds ;

    float calorie;

    public Runnable runnable = new Runnable() {

        public void run() {

            if(isPause){
                StartTime = SystemClock.uptimeMillis();
            }else{
                MillisecondTime = (SystemClock.uptimeMillis() - StartTime) + PauseTime;
            }

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            m_sec = (Minutes*60) + Seconds;

            MilliSeconds = (int) (UpdateTime % 1000);

            time.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if(intent.getSerializableExtra("TypeExercice") == null){

            startActivity(new Intent(this, ChoixDeTypeExerciceActivity.class));

            Toast.makeText(this, getResources().getString(R.string.choix_exo_avant), Toast.LENGTH_SHORT).show();
            return;
        }

        setContentView(R.layout.activity_commencer_exercice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TypeExercice typeExo = (TypeExercice)intent.getSerializableExtra("TypeExercice");
        getSupportActionBar().setTitle(" "+typeExo.getNom());
        getSupportActionBar().setIcon(typeExo.getIcon());


        vitesse = (TextView)findViewById(R.id.vitesse_id);
        distance = (TextView)findViewById(R.id.distance_id);
        time = (TextView)findViewById(R.id.time_id);
        calories = (TextView)findViewById(R.id.calories_id);

        positionsForVitesse = new ArrayList<Position>();

        // Initialisation de l'Exercice
        monExercice = new Exercice();
        monExercice.setTypeExercice((TypeExercice)intent.getSerializableExtra("TypeExercice"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.ic_start);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                SimpleDateFormat sdf;
                sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

                if(!isStart){
                    getTime();
                    isStart = true;
                    fab.setImageResource(R.mipmap.ic_stop);

                    if(Float.valueOf(vitesse.getText().toString()) == 0){
                        isPause = true;
                    }else{
                        isPause = false;
                    }

                    Date date = new Date();
                    monExercice.setHeure_debut(sdf.format(date));

                }else{
                    // Problème Android 8.0 et 4.2.2
                    fab.setImageResource(R.mipmap.ic_start);

                    isStart = false;

                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);

                    monExercice.setId_utilisateur(VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur().getId());
                    monExercice.setTime(time.getText().toString());
                    monExercice.setDistance(Float.valueOf(distance.getText().toString()));
                    monExercice.setCalories(Float.valueOf(calories.getText().toString()));

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    monExercice.setDate(dateFormat.format(date));
                    monExercice.setHeure_fin(sdf.format(date));

                    ExerciceDAO exerciceDAO = new ExerciceDAO(CommencerExerciceActivity.this);
                    exerciceDAO.openW();
                    exerciceDAO.ajouterUnExercice(monExercice);
                    exerciceDAO.close();

                    new AsyncSavePositions().execute();
                }

            }
        });

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GPS);
        } else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, CommencerExerciceActivity.this);
        }

        handler = new Handler() ;

        calorie = 0;

        heart_rate = 0;

        showInformation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, CommencerExerciceActivity.this);

        } else {
            //Toast.makeText(this, "Permission Refusé", Toast.LENGTH_SHORT).show();
        }
    }

    public void afficherParcours() {

        ArrayList<Position> positions = VariablesGlobales.getInstance().getMesPositions();

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

            mark.position(lastLatLng);
            mark.title(getResources().getString(R.string.current_position));
            mMap.addMarker(mark);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLatLng));

            getDistance();
        }

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
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);

        Location location = new Location(LocationManager.GPS_PROVIDER);
        // Centrer la caméra sur la position actuelle
        LatLng position_actuelle = new LatLng(location.getLatitude(), location.getLongitude());
        //mMap.addMarker(new MarkerOptions().position(position_actuelle).title("Yoooo what are you doing ?"));
        mMap.setMinZoomPreference(14);
        /*mMap.setMaxZoomPreference(12);*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(position_actuelle));
    }

    @Override
    public void onLocationChanged(Location location) {

        if(isStart) {
            location.setAccuracy(Criteria.ACCURACY_HIGH);
            Position position = new Position();
            position.setLat(location.getLatitude());
            position.setLng(location.getLongitude());
            position.setSecondes((int) (millis / 1000));

            getVitesse(location);

            //if(Float.valueOf(vitesse.getText().toString()) != 0) {
                positionsForVitesse.add(position);
            //}

            ArrayList<Position> positions = VariablesGlobales.getInstance().getMesPositions();

            if (positions.size() > 1) {
                Location lastLocation = new Location(LocationManager.GPS_PROVIDER);
                lastLocation.setLatitude(positions.get(positions.size() - 1).getLat());
                lastLocation.setLongitude(positions.get(positions.size() - 1).getLng());

                if (location.distanceTo(lastLocation) >= DISTANCE_MIN) {
                    positions.add(position);
                }
            } else {
                positions.add(position);
            }

            VariablesGlobales.getInstance().setMesPositions(positions);

            afficherParcours();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Provider Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Provider Disabled", Toast.LENGTH_SHORT).show();
    }

    public void getDistance(){
        monExercice.setDistance(0);

        ArrayList<Position> positions = VariablesGlobales.getInstance().getMesPositions();

        if(positions.size() > 1){
            for(int i = 0; i < positions.size() - 1; i++){
                Location startLocation = new Location(LocationManager.GPS_PROVIDER);
                startLocation.setLatitude(positions.get(i).getLat());
                startLocation.setLongitude(positions.get(i).getLng());

                Location endLocation = new Location(LocationManager.GPS_PROVIDER);
                endLocation.setLatitude(positions.get(i + 1).getLat());
                endLocation.setLongitude(positions.get(i + 1).getLng());

                float distance = (int)(monExercice.getDistance() + startLocation.distanceTo(endLocation));
                monExercice.setDistance(distance);
            }
        }

        distance.setText(""+(int)monExercice.getDistance());
    }

    public void getTime(){

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    public void pause(View v){
        if(isPause){
            isPause = false;
        }else{
            PauseTime = MillisecondTime;
            isPause = true;
        }
    }

    public void getVitesse(Location endLocation){

        int taille = positionsForVitesse.size();

        float distance = 0;

        if(positionsForVitesse.size() > 1){

                Location startLocation = new Location(LocationManager.GPS_PROVIDER);
                startLocation.setLatitude(positionsForVitesse.get(taille - 1).getLat());
                startLocation.setLongitude(positionsForVitesse.get(taille - 1).getLng());

                distance = (int)(startLocation.distanceTo(endLocation));
        }

        if(distance == 0){
            isPause = true;
            PauseTime = MillisecondTime;
        }else{
            isPause = false;
        }

        vitesse.setText(""+(distance)+"");

        /*
         Calcul des calories dépensées au cours de l'activité.
          */
        Utilisateur utilisateur = VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur();

        if(monExercice.getTypeExercice().getNom().equals(getResources().getString(R.string.marche))){
            heart_rate = (AppConstantes.WALK_HEART_RATE_MOY);
        } else if (monExercice.getTypeExercice().getNom().equals(getResources().getString(R.string.course))) {
            heart_rate = (AppConstantes.RUN_HEART_RATE_MOY);
        } else if(monExercice.getTypeExercice().getNom().equals(getResources().getString(R.string.velo))){
            heart_rate = (AppConstantes.BICYCLE_HEART_RATE_MOY);
        }

        Toast.makeText(this, ""+heart_rate, Toast.LENGTH_SHORT).show();

        if(utilisateur.getGenre() == 1){// Source : http://fitnowtraining.com/2012/01/formula-for-calories-burned/
            calorie = (int)(((utilisateur.getAge() * 0.2017) - (utilisateur.getPoids() * 0.09036) + ((heart_rate) * 0.6309) - 55.0969)*((float)m_sec/4.184));
        }else{
            calorie = (int)(((utilisateur.getAge() * 0.2017) - (utilisateur.getPoids() * 0.05741) + ((heart_rate) * 0.4472) - 20.4022)*((float)m_sec/4.184));
        }

        calories.setText(""+(int)calorie);
    }

    public void sauvegarderLesPositionDeExercice(Exercice exercice){

        ExerciceDAO exerciceDAO = new ExerciceDAO(this);
        exerciceDAO.openR();

        ArrayList<Exercice> listExercice = new ArrayList<>();
        listExercice = exerciceDAO.getAllUtilisateurExercices(VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur().getId());

        exerciceDAO.close();

        int id_last_exercice = 0;
        if(listExercice != null) {
            id_last_exercice = listExercice.get(listExercice.size() - 1).getId();
        }

        PositionDAO positionDAO = new PositionDAO(this);
        positionDAO.openW();

        exercice.setPositions(VariablesGlobales.getInstance().getMesPositions());

        for(int i = 0; i < exercice.getPositions().size(); i++){
            Position position = new Position();
            position.setLat(exercice.getPositions().get(i).getLat());
            position.setLng(exercice.getPositions().get(i).getLng());

            if(listExercice != null) {
                position.setId_exercice(id_last_exercice);
            }else{
                position.setId_exercice(id_last_exercice + 1);
            }
            positionDAO.ajouterUnePosition(position);
        }

        positionDAO.close();
    }

    private class AsyncSavePositions extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = new ProgressDialog(CommencerExerciceActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            //Montrer la progression
            progressDialog.setMessage(getResources().getString(R.string.save_parcours));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params){

            sauvegarderLesPositionDeExercice(monExercice);

            //Toast.makeText(CommencerExerciceActivity.this, getResources().getString(R.string.congratulation), Toast.LENGTH_SHORT).show();

            VariablesGlobales.getInstance().setMesPositions(new ArrayList<Position>());

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            progressDialog.dismiss();

            startActivity(new Intent(CommencerExerciceActivity.this, MesActivitesJournalieresActivity.class));
            finish();
        }
    }

    public void showInformation(){

        // Creation de l'AlertDialog
        final android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(this);

        // On lui donne un titre
        adb.setTitle(getResources().getString(R.string.information_title));

        adb.setMessage(getResources().getString(R.string.info_msg_2));

        // On modifie l'icône de l'AlertDialog
        adb.setIcon(android.R.drawable.ic_menu_info_details);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        adb.show();
    }
}
