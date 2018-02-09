package com.app.deguenon_cresus.healthy.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.deguenon_cresus.healthy.apps.MaPreference;
import com.app.deguenon_cresus.healthy.apps.VariablesGlobales;
import com.app.deguenon_cresus.healthy.models.Utilisateur;
import com.app.deguenon_cresus.healthy.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_GPS = 100;

    private TextView nom_prenom;

    private FloatingActionButton fab_edit_picture;

    private CircleImageView profil_image;

    private ImageView image;

    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        utilisateur = VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur();

        if(utilisateur == null){
            startActivity(new Intent(this, ConnexionActivity.class));
            finish();
        }

        createFolders();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        image = (ImageView)findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformation();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profil_image =(CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profil_image);

        fab_edit_picture = (FloatingActionButton) navigationView.getHeaderView(0).findViewById(R.id.fab_edit_picture);
        fab_edit_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.pick_picture)), 1);
            }
        });


        nom_prenom = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nom_prenom_utilisateur);
        nom_prenom.setText(utilisateur.getPrenom()+" "+utilisateur.getNom());
        chargerProfilImage();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GPS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GPS);
            }
            //Toast.makeText(this, "Permission Refusé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_mon_profil){
            startActivity(new Intent(MainActivity.this, GestionProfilActivity.class));
        } else if (id == R.id.nav_commencer_exercice) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, ChoixDeTypeExerciceActivity.class));
        } else if (id == R.id.nav_mes_activites_du_jour) {
            startActivity(new Intent(MainActivity.this, MesActivitesJournalieresActivity.class));
        } else if (id == R.id.nav_statistiques) {
            startActivity(new Intent(MainActivity.this, StatistiquesActivity.class));
        } else if(id == R.id.nav_help){
            startActivity(new Intent(MainActivity.this, AideActivity.class));
        } else if (id == R.id.nav_deconnexion) {

            MaPreference maPreference = VariablesGlobales.getInstance().getMaPreference();
            maPreference.nettoyerPreference();
            VariablesGlobales.getInstance().setMaPreference(maPreference);

            finish();
            startActivity(new Intent(this, ConnexionActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createFolders(){
        File healthDir = new File("/sdcard/Healthy");

        if(!healthDir.mkdirs()){
            //Toast.makeText(this, "Directory not created", Toast.LENGTH_SHORT).show();
        }

        File healthImagesDir = new File(healthDir.getAbsolutePath().toString(),"Images");

        if(!healthImagesDir.exists())
            healthImagesDir.mkdir();
    }

    @Override
    protected void onActivityResult(int RC, int RQC, final Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Bitmap.createScaledBitmap(bitmap, 200, 200, false);

                profil_image.setImageBitmap(bitmap);

                sauvegarderProfilImage(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sauvegarderProfilImage(Bitmap bitmap){

        FileOutputStream out = null;

        try {
            out = new FileOutputStream("/sdcard/Healthy/Images/"+""
                    + File.separator+""+nom_prenom.getText().toString()+".png");

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void chargerProfilImage(){

        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Healthy/Images/" + nom_prenom.getText().toString() + ".png");

        if(bitmap != null){
            profil_image.setImageBitmap(bitmap);
        }
    }

    public void showInformation(){

        // Creation de l'AlertDialog
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);

        // On lui donne un titre
        adb.setTitle(getResources().getString(R.string.information_title));

        adb.setMessage(getResources().getString(R.string.info_msg1));

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
