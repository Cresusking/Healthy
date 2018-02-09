package com.app.deguenon_cresus.healthy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.deguenon_cresus.healthy.adapters.MesActivitesJournaliereAdapter;
import com.app.deguenon_cresus.healthy.apps.VariablesGlobales;
import com.app.deguenon_cresus.healthy.models.Exercice;
import com.app.deguenon_cresus.healthy.sqlites.ExerciceDAO;
import com.app.deguenon_cresus.healthy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class MesActivitesJournalieresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MesActivitesJournaliereAdapter adapter;

    private ImageButton btn_precedent;

    private ImageButton btn_suivant;

    private TextView month_year;

    private ArrayList<Exercice> listExerciceJournalier;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mes_activites_journalieres);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.activites_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        date = new Date();

        listExerciceJournalier = new ArrayList<>();
        recupererExercicesJournalier(date);

        adapter = new MesActivitesJournaliereAdapter(this, listExerciceJournalier);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // Ecouteur (Listener) placé sur la liste graphique pour détecter le clic
        recyclerView.addOnItemTouchListener(new MesActivitesJournaliereAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new MesActivitesJournaliereAdapter.ClickListener() {

            Intent intent;

            @Override
            public void onClick(View view, int position) {
                intent = new Intent(MesActivitesJournalieresActivity.this, CarteActiviteActivity.class);
                intent.putExtra("Exercice", listExerciceJournalier.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                // Prévoir un truc pour l'appui long ou pas.
            }
        }));

        btn_precedent = (ImageButton)findViewById(R.id.btn_precedent);
        btn_precedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJourChoisi(date, -1);
            }
        });
        btn_suivant = (ImageButton)findViewById(R.id.btn_suivant);
        btn_suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJourChoisi(date, 1);
            }
        });

        month_year = (TextView)findViewById(R.id.date_panel);
        month_year.setText(""+dateFormat.format(date));

        adapter.notifyDataSetChanged();
    }

    public void recupererExercicesJournalier(Date date){

        ArrayList<Exercice> listeTemporaire = new ArrayList<>();

        ExerciceDAO exerciceDAO = new ExerciceDAO(this);
        exerciceDAO.openR();

        if(listExerciceJournalier.size() > 0){
            listExerciceJournalier.removeAll(listExerciceJournalier);
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //Toast.makeText(this, ""+dateFormat.format(date), Toast.LENGTH_SHORT).show();

        listeTemporaire = exerciceDAO.getAllDayExercices(VariablesGlobales.getInstance().getMaPreference().recupererUtilisateur().getId(), dateFormat.format(date));

        if(listeTemporaire == null){
            listeTemporaire = new ArrayList<>();
        }

        exerciceDAO.close();

        for(int i = 0; i < listeTemporaire.size(); i++){
            listExerciceJournalier.add(listeTemporaire.get(i));
        }
    }

    public void getJourChoisi(Date date, int jour){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            date.setDate(date.getDate() + jour);

            month_year.setText(dateFormat.format(date));

            date = (Date)dateFormat.parse(month_year.getText().toString());

            recupererExercicesJournalier(date);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
