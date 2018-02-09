package com.app.deguenon_cresus.healthy.apps;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.deguenon_cresus.healthy.models.Utilisateur;


/**
 * Created by DEGUENON_Cresus on 19/11/2017.
 */

public class MaPreference {

    public String TAG = MaPreference.class.getSimpleName();

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private Context mContext;

    private int PRIVATE_MODE = 0;

    public MaPreference(Context context){
        this.mContext = context;
        preferences = context.getSharedPreferences(AppConstantes.USER_PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void enregistrerUnUtilisateur(Utilisateur utilisateur){
        editor.putInt(AppConstantes.KEY_USER_ID, utilisateur.getId());
        editor.putString(AppConstantes.KEY_USER_NOM, utilisateur.getNom());
        editor.putString(AppConstantes.KEY_USER_PRENOM, utilisateur.getPrenom());
        editor.putString(AppConstantes.KEY_USER_EMAIL, utilisateur.getEmail());
        editor.putString(AppConstantes.KEY_USER_PASSWORD, utilisateur.getPassword());
        editor.putInt(AppConstantes.KEY_USER_POIDS, utilisateur.getPoids());
        editor.putInt(AppConstantes.KEY_USER_AGE, utilisateur.getAge());
        editor.putInt(AppConstantes.KEY_USER_RYTHME_CARDIAQUE, utilisateur.getRythme_cardiaque());
        editor.putInt(AppConstantes.KEY_USER_GENRE, utilisateur.getGenre());

        editor.commit();
    }

    public void nettoyerPreference(){
        editor.clear();
        editor.commit();
    }

    public Utilisateur recupererUtilisateur() {
        if (preferences.getString(AppConstantes.KEY_USER_NOM, null) != null) {
            int id, poids, age, rythme_cardiaque, genre;
            String nom, prenom, email, password;
            id = preferences.getInt(AppConstantes.KEY_USER_ID, 0);
            nom = preferences.getString(AppConstantes.KEY_USER_NOM, null);
            prenom = preferences.getString(AppConstantes.KEY_USER_PRENOM, null);
            email = preferences.getString(AppConstantes.KEY_USER_EMAIL, null);
            password = preferences.getString(AppConstantes.KEY_USER_PASSWORD, null);
            poids = preferences.getInt(AppConstantes.KEY_USER_POIDS, 0);
            age = preferences.getInt(AppConstantes.KEY_USER_AGE, 0);
            rythme_cardiaque = preferences.getInt(AppConstantes.KEY_USER_RYTHME_CARDIAQUE, 80);
            genre = preferences.getInt(AppConstantes.KEY_USER_GENRE, 0);

            Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, password, poids, age, rythme_cardiaque, genre);
            return utilisateur;
        }
        return null;
    }
}
