package com.app.deguenon_cresus.healthy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.deguenon_cresus.healthy.apps.AppConstantes;
import com.app.deguenon_cresus.healthy.apps.VariablesGlobales;
import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.apps.MaPreference;
import com.app.deguenon_cresus.healthy.models.Utilisateur;
import com.app.deguenon_cresus.healthy.sqlites.UtilisateurDAO;


/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class InscriptionActivity extends AppCompatActivity {

    private EditText input_nom;
    private EditText input_prenom;
    private EditText input_email;
    private EditText input_password;
    private EditText input_poids;
    private EditText input_age;
    private EditText input_rythme_cardiaque;
    private Spinner input_genre;

    private Button btn_inscription;

    private TextView test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inscription);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.inscription_titre));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        input_nom = (EditText)findViewById(R.id.input_nom);
        input_prenom = (EditText)findViewById(R.id.input_prenom);
        input_email = (EditText)findViewById(R.id.input_email);
        input_password = (EditText)findViewById(R.id.input_password);
        input_poids = (EditText)findViewById(R.id.input_poids);
        input_age = (EditText)findViewById(R.id.input_age);
        input_rythme_cardiaque = (EditText)findViewById(R.id.input_rythme_cardiaque);
        input_rythme_cardiaque.setVisibility(View.GONE);

        input_genre = (Spinner)findViewById(R.id.input_genre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choix, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_genre.setAdapter(adapter);

        btn_inscription = (Button)findViewById(R.id.btn_inscription);
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effectuerUneInscription();
            }
        });
    }

    public void effectuerUneInscription() {
        if(TextUtils.isEmpty(input_nom.getText().toString())
                || TextUtils.isEmpty(input_prenom.getText().toString())
                || TextUtils.isEmpty(input_email.getText().toString())
                || TextUtils.isEmpty(input_password.getText().toString())
                || TextUtils.isEmpty(input_poids.getText().toString())
                || TextUtils.isEmpty(input_age.getText().toString())){

            Toast.makeText(this, getResources().getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
            return;
        }

        if(Integer.valueOf(input_age.getText().toString()) > AppConstantes.AGE_LIMITE || Integer.valueOf(input_age.getText().toString()) < 1){

            Toast.makeText(this, getResources().getString(R.string.age_limite), Toast.LENGTH_SHORT).show();
            return;
        }

        /*try{
            int test = Integer.valueOf(input_poids.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "Votre poids doit etre un Entier", Toast.LENGTH_SHORT).show();
        }*/

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO(this);
        utilisateurDAO.openW();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(input_nom.getText().toString());
        utilisateur.setPrenom(input_prenom.getText().toString());
        utilisateur.setEmail(input_email.getText().toString());
        utilisateur.setPassword(input_password.getText().toString());
        utilisateur.setPoids(Integer.valueOf(input_poids.getText().toString()));
        utilisateur.setAge(Integer.valueOf(input_age.getText().toString()));
        utilisateur.setRythme_cardiaque(AppConstantes.HEART_RATE);
        utilisateur.setGenre(Integer.valueOf(input_genre.getSelectedItem().toString().charAt(0)));

        utilisateurDAO.ajouterUnUtilisateur(utilisateur);
        utilisateurDAO.close();

        utilisateurDAO.openR();

        MaPreference maPreference = VariablesGlobales.getInstance().getMaPreference();
        maPreference.enregistrerUnUtilisateur(utilisateurDAO.getUtilisateur(utilisateur.getEmail(), utilisateur.getPassword()));
        VariablesGlobales.getInstance().setMaPreference(maPreference);

        utilisateurDAO.close();

        Toast.makeText(this, getResources().getString(R.string.confirmation_user), Toast.LENGTH_SHORT).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}