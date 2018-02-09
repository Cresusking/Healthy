package com.app.deguenon_cresus.healthy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.app.deguenon_cresus.healthy.R;
import com.app.deguenon_cresus.healthy.adapters.ChoixTypeExerciceAdapter;
import com.app.deguenon_cresus.healthy.models.TypeExercice;
import com.app.deguenon_cresus.healthy.sqlites.TypeExerciceDAO;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 16/11/2017.
 */

public class ChoixDeTypeExerciceActivity extends AppCompatActivity {

    private ChoixTypeExerciceAdapter adapter;

    private RecyclerView recyclerView;

    private ArrayList<TypeExercice> typeExerciceArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choix_type_exercice);

        recupererTypesExercices();

        if(typeExerciceArrayList == null){
            insererTypeExercice();

            recupererTypesExercices();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.choix_exo_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        adapter = new ChoixTypeExerciceAdapter(this, typeExerciceArrayList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new ChoixTypeExerciceAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new ChoixTypeExerciceAdapter.ClickListener() {

            Intent intent;

            @Override
            public void onClick(View view, int position) {

                intent = new Intent(ChoixDeTypeExerciceActivity.this, CommencerExerciceActivity.class);
                // Transmission d'information à l"Activité de redirection
                intent.putExtra("TypeExercice", typeExerciceArrayList.get(position));
                // Lancement de l'activité de redirection
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void insererTypeExercice() {

        TypeExerciceDAO typeExerciceDAO = new TypeExerciceDAO(this);
        typeExerciceDAO.openW();

        typeExerciceDAO.ajouterUnTypeExercice(new TypeExercice(R.mipmap.ic_walk, getResources().getString(R.string.marche)));
        typeExerciceDAO.ajouterUnTypeExercice(new TypeExercice(R.mipmap.ic_run, getResources().getString(R.string.course)));
        typeExerciceDAO.ajouterUnTypeExercice(new TypeExercice(R.mipmap.ic_bicycle, getResources().getString(R.string.velo)));

        typeExerciceDAO.close();
    }

    public void recupererTypesExercices() {
        TypeExerciceDAO typeExerciceDAO = new TypeExerciceDAO(this);
        typeExerciceDAO.openR();

        typeExerciceArrayList = new ArrayList<TypeExercice>();
        typeExerciceArrayList = typeExerciceDAO.getAllTypeExercices();

        typeExerciceDAO.close();
    }
}
