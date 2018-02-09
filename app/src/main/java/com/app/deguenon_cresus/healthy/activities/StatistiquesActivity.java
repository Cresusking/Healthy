package com.app.deguenon_cresus.healthy.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.app.deguenon_cresus.healthy.apps.VariablesGlobales;
import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.models.Exercice;
import com.app.deguenon_cresus.healthy.sqlites.ExerciceDAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class StatistiquesActivity extends AppCompatActivity {

    private TextView month_year;
    private TextView distance_totale;
    private TextView walk_nb;
    private TextView run_nb;
    private TextView bicycle_nb;
    private TextView perf_calorie;
    private TextView calorie;

    private ImageButton btn_precedent;
    private ImageButton btn_suivant;

    private ArrayList<Exercice> exercicesDuMois;
    private ArrayList<Exercice> exercicesDuMoisDernier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activite_statistiques);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.stat_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        Date date = new Date();

        month_year = (TextView)findViewById(R.id.date_panel);
        month_year.setText(""+dateFormat.format(date));

        distance_totale = (TextView)findViewById(R.id.distance_totale);
        walk_nb = (TextView)findViewById(R.id.walk_nb);
        run_nb = (TextView)findViewById(R.id.run_nb);
        bicycle_nb = (TextView)findViewById(R.id.bicycle_nb);
        perf_calorie = (TextView)findViewById(R.id.perf_calorie);
        calorie = (TextView)findViewById(R.id.id_calorie);

        btn_precedent = (ImageButton)findViewById(R.id.btn_precedent);
        btn_precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoisStatistique(-1);
            }
        });
        btn_suivant = (ImageButton)findViewById(R.id.btn_suivant);
        btn_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoisStatistique(1);
            }
        });


        try {
            date = (Date)dateFormat.parse(month_year.getText().toString());

            recupererExercicesMensuel(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void recupererExercicesMensuel(Date date){

        ExerciceDAO exerciceDAO = new ExerciceDAO(this);
        exerciceDAO.openR();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        int walk = 0, run = 0, bicycle = 0;
        float distance = 0, calorie_du_mois = 0, calorie_du_mois_dernier = 0;

        exercicesDuMois = new ArrayList<Exercice>();
        exercicesDuMois = exerciceDAO.getAllUtilisateurExercices(VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur().getId());

        exerciceDAO.close();

        if(exercicesDuMois == null){

        }else{

            for(int i = 0; i < exercicesDuMois.size(); i++){

                try {
                    Date currentDate = new Date();
                    currentDate = (Date)dateFormat.parse(exercicesDuMois.get(i).getDate());

                    Log.e("Stat", ""+currentDate.getMonth()+"/"+currentDate.getYear());

                    if(currentDate.getMonth() == date.getMonth() && currentDate.getYear() == date.getYear()){

                        distance += exercicesDuMois.get(i).getDistance();
                        calorie_du_mois += exercicesDuMois.get(i).getCalories();

                        if(exercicesDuMois.get(i).getTypeExercice().getNom().equals(getResources().getString(R.string.marche))){
                            walk += exercicesDuMois.get(i).getDistance();
                        } else if (exercicesDuMois.get(i).getTypeExercice().getNom().equals(getResources().getString(R.string.course))) {
                            run += exercicesDuMois.get(i).getDistance();
                        } else if(exercicesDuMois.get(i).getTypeExercice().getNom().equals(getResources().getString(R.string.velo))){
                            bicycle += exercicesDuMois.get(i).getDistance();
                        }

                    }else if(date.getMonth() == 0){
                        if(currentDate.getMonth() == 11 && currentDate.getYear() == (date.getYear() - 1)){
                            calorie_du_mois_dernier += exercicesDuMois.get(i).getCalories();
                        }
                    }
                    else if(currentDate.getMonth() == (date.getMonth() - 1) && (currentDate.getYear() == date.getYear())){
                        calorie_du_mois_dernier += exercicesDuMois.get(i).getCalories();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        distance_totale.setText(""+distance+"\nm");
        walk_nb.setText(""+walk+"\nm");
        run_nb.setText(""+run+"\nm");
        bicycle_nb.setText(""+bicycle+"\nm");
        calorie.setText(calorie_du_mois+" cal");

        if(calorie_du_mois > calorie_du_mois_dernier) {
            perf_calorie.setText(" + " + (calorie_du_mois - calorie_du_mois_dernier));
            perf_calorie.setTextColor(Color.GREEN);
        }else if(calorie_du_mois == calorie_du_mois_dernier){
            perf_calorie.setText("" +(calorie_du_mois - calorie_du_mois_dernier));
            perf_calorie.setTextColor(Color.GRAY);
        }else{
            perf_calorie.setText("" + (calorie_du_mois - calorie_du_mois_dernier));
            perf_calorie.setTextColor(Color.RED);
        }

    }

    public void getMoisStatistique(int nb_mois){

        DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        Date date = new Date();

        try {
            date = (Date)dateFormat.parse(month_year.getText().toString());
            date.setMonth(date.getMonth() + nb_mois);

            month_year.setText(dateFormat.format(date));

            recupererExercicesMensuel(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
