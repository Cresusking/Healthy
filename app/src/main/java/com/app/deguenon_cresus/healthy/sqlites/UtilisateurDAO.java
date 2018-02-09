package com.app.deguenon_cresus.healthy.sqlites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.deguenon_cresus.healthy.apps.DBConstantes;
import com.app.deguenon_cresus.healthy.models.Utilisateur;


/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class UtilisateurDAO {

    // Declaration d'une base de données SQLiteDatabase
    private SQLiteDatabase bdd;

    // Déclaration de la Classe MaBase
    private MaBase maBase;

    public UtilisateurDAO(Context context){

        maBase = new MaBase(context, DBConstantes.BD_NOM, null, DBConstantes.VERSION);
    }

    // Méthode d'ouverture en mode lecture de la base de données
    public void openR(){
        bdd = maBase.getReadableDatabase();
    }

    // Méthode d'ouverture en mode écriture de la base de données
    public void openW(){
        bdd = maBase.getWritableDatabase();
    }

    // Méthode de fermeture de la base de données
    public void close(){
        bdd.close();
    }

    // Méthode d'Ajout
    public long ajouterUnUtilisateur(Utilisateur utilisateur){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.UTILISATEUR_NOM, utilisateur.getNom());
        values.put(DBConstantes.UTILISATEUR_PRENOM, utilisateur.getPrenom());
        values.put(DBConstantes.UTILISATEUR_EMAIL, utilisateur.getEmail());
        values.put(DBConstantes.UTILISATEUR_PASSWORD, utilisateur.getPassword());
        values.put(DBConstantes.UTILISATEUR_POIDS, utilisateur.getPoids());
        values.put(DBConstantes.UTILISATEUR_AGE, utilisateur.getAge());
        values.put(DBConstantes.UTILISATEUR_RYTHME_CARDIAQUE, utilisateur.getRythme_cardiaque());
        values.put(DBConstantes.UTILISATEUR_GENRE, utilisateur.getGenre());

        return bdd.insert(DBConstantes.TABLE_UTILISATEUR, null, values);
    }

    // Méthode de mis à jour
    public long modifierUnUtilisateur(int id, Utilisateur utilisateur){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.UTILISATEUR_NOM, utilisateur.getNom());
        values.put(DBConstantes.UTILISATEUR_PRENOM, utilisateur.getPrenom());
        values.put(DBConstantes.UTILISATEUR_EMAIL, utilisateur.getEmail());
        values.put(DBConstantes.UTILISATEUR_PASSWORD, utilisateur.getPassword());
        values.put(DBConstantes.UTILISATEUR_POIDS, utilisateur.getPoids());
        values.put(DBConstantes.UTILISATEUR_AGE, utilisateur.getAge());
        values.put(DBConstantes.UTILISATEUR_RYTHME_CARDIAQUE, utilisateur.getRythme_cardiaque());
        values.put(DBConstantes.UTILISATEUR_GENRE, utilisateur.getGenre());

        return bdd.update(DBConstantes.TABLE_UTILISATEUR, values, DBConstantes.UTILISATEUR_ID +" = "+id, null);
    }

    // Méthode de suppression
    public int supprimerUnUtilisateur(int id){
        return bdd.delete(DBConstantes.TABLE_UTILISATEUR, DBConstantes.UTILISATEUR_ID +" = "+id, null);
    }

    // Méthode de recherche avec l'identifiant
    public Utilisateur getUtilisateur(int id){
        Cursor c = bdd.query(DBConstantes.TABLE_UTILISATEUR,
                new String[]{DBConstantes.UTILISATEUR_ID,
                        DBConstantes.UTILISATEUR_NOM,
                        DBConstantes.UTILISATEUR_PRENOM,
                        DBConstantes.UTILISATEUR_EMAIL,
                        DBConstantes.UTILISATEUR_PASSWORD,
                        DBConstantes.UTILISATEUR_POIDS,
                        DBConstantes.UTILISATEUR_AGE,
                        DBConstantes.UTILISATEUR_RYTHME_CARDIAQUE,
                        DBConstantes.UTILISATEUR_GENRE},
                DBConstantes.UTILISATEUR_ID+" = "+id, null, null, null, null);

        return cursorToUtilisateur(c);
    }

    // Méthode de recherche ave l'email et le mot de passe
    public Utilisateur getUtilisateur(String email, String password){
        Cursor c = bdd.query(DBConstantes.TABLE_UTILISATEUR,
                new String[]{DBConstantes.UTILISATEUR_ID,
                        DBConstantes.UTILISATEUR_NOM,
                        DBConstantes.UTILISATEUR_PRENOM,
                        DBConstantes.UTILISATEUR_EMAIL,
                        DBConstantes.UTILISATEUR_PASSWORD,
                        DBConstantes.UTILISATEUR_POIDS,
                        DBConstantes.UTILISATEUR_AGE,
                        DBConstantes.UTILISATEUR_RYTHME_CARDIAQUE,
                        DBConstantes.UTILISATEUR_GENRE},
                DBConstantes.UTILISATEUR_EMAIL+" LIKE \""+email+"\" AND "+DBConstantes.UTILISATEUR_PASSWORD+" = \""+password+"\"", null, null, null, null);

        return cursorToUtilisateur(c);
    }

    // Méthode de lecture d'un curseur
    private Utilisateur cursorToUtilisateur(Cursor c){

        if(c.getCount() == 0)
            return null;

        c.moveToFirst();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(c.getInt(DBConstantes.UTILISATEUR_ID_ID));
        utilisateur.setNom(c.getString(DBConstantes.UTILISATEUR_NOM_ID));
        utilisateur.setPrenom(c.getString(DBConstantes.UTILISATEUR_PRENOM_ID));
        utilisateur.setEmail(c.getString(DBConstantes.UTILISATEUR_EMAIL_ID));
        utilisateur.setPassword(c.getString(DBConstantes.UTILISATEUR_PASSWORD_ID));
        utilisateur.setPoids(c.getInt(DBConstantes.UTILISATEUR_POIDS_ID));
        utilisateur.setAge(c.getInt(DBConstantes.UTILISATEUR_AGE_ID));
        utilisateur.setRythme_cardiaque(c.getInt(DBConstantes.UTILISATEUR_RYTHME_CARDIAQUE_ID));
        utilisateur.setGenre(c.getInt(DBConstantes.UTILISATEUR_GENRE_ID));

        // On ferme le curseur
        c.close();

        // On retourne les informations de l'utilisateur trouvé
        return utilisateur;
    }
}
