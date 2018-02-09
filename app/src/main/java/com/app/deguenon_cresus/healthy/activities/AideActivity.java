package com.app.deguenon_cresus.healthy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.app.deguenon_cresus.healthy.R;

/**
 * Created by DEGUENON_Cresus on 02/01/2018.
 */

public class AideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Aide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
