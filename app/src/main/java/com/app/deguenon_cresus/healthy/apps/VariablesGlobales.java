package com.app.deguenon_cresus.healthy.apps;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.app.deguenon_cresus.healthy.models.Position;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 10/11/2017.
 */

public class VariablesGlobales extends Application{

    private static volatile VariablesGlobales mInstance = null;

    private ArrayList<Position> mesPositions;

    private MaPreference maPreference;

    private RequestQueue requestQueue;

    public static final String TAG = VariablesGlobales.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        mesPositions = new ArrayList<Position>();
        maPreference = new MaPreference(this);
        mInstance = this;
    }

    public static synchronized VariablesGlobales getInstance(){
        return mInstance;
    }

    public ArrayList<Position> getMesPositions() {
        return mesPositions;
    }

    public void setMesPositions(ArrayList<Position> mesPositions) {
        this.mesPositions = mesPositions;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public MaPreference getMaPreference() {
        return maPreference;
    }

    public void setMaPreference(MaPreference maPreference) {
        this.maPreference = maPreference;
    }
}
