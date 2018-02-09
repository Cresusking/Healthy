package com.app.deguenon_cresus.healthy.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.deguenon_cresus.healthy.apps.VariablesGlobales;
import com.app.deguenon_cresus.healthy.sqlites.UtilisateurDAO;
import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.apps.MaPreference;
import com.app.deguenon_cresus.healthy.models.Utilisateur;


/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class ConnexionActivity extends AppCompatActivity {

    private EditText input_email;

    private EditText input_password;

    private Button btn_connexion;

    private Button btn_inscription;

    /* CHECKING PERMISSION */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur() != null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        setContentView(R.layout.activity_connexion);

        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);

        btn_connexion = (Button)findViewById(R.id.btn_connexion);
        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });

        btn_inscription = (Button)findViewById(R.id.btn_inscription);
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscription();
            }
        });

        verifyStoragePermissions(this);
    }

    public void connect(){

        if(TextUtils.isEmpty(input_email.getText().toString())
                || TextUtils.isEmpty(input_password.getText().toString())){

            Toast.makeText(this, getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
            return;
        }

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO(this);
        utilisateurDAO.openR();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurDAO.getUtilisateur(input_email.getText().toString(), input_password.getText().toString());

        if(utilisateur == null){
            Toast.makeText(this, getResources().getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
            utilisateur = new Utilisateur();
            return;
        }else{

            MaPreference maPreference = VariablesGlobales.getInstance().getMaPreference();
            maPreference.enregistrerUnUtilisateur(utilisateur);
            VariablesGlobales.getInstance().setMaPreference(maPreference);

            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void inscription(){
        startActivity(new Intent(this, InscriptionActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){

        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity){

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            //Toast.makeText(activity, "Passage par permission", Toast.LENGTH_SHORT).show();
        }
    }
}
