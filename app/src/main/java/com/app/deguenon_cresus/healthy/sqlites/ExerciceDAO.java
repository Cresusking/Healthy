package com.app.deguenon_cresus.healthy.sqlites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.app.deguenon_cresus.healthy.apps.DBConstantes;
import com.app.deguenon_cresus.healthy.models.Exercice;
import com.app.deguenon_cresus.healthy.models.Position;
import com.app.deguenon_cresus.healthy.models.TypeExercice;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class ExerciceDAO {

    // Declaration d'une base de données SQLiteDatabase
    private SQLiteDatabase bdd;

    // Déclaration de la Classe MaBase
    private MaBase maBase;

    private Context context;

    public ExerciceDAO(Context context){

        this.context = context;
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
    public long ajouterUnExercice(Exercice exercice){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.EXERCICE_ID_UTILISATEUR, exercice.getId_utilisateur());
        values.put(DBConstantes.EXERCICE_DISTANCE, exercice.getDistance());
        values.put(DBConstantes.EXERCICE_TIME, exercice.getTime());
        values.put(DBConstantes.EXERCICE_CALORIES, exercice.getCalories());
        values.put(DBConstantes.EXERCICE_TYPE_EXERCICE, exercice.getTypeExercice().getId());
        values.put(DBConstantes.EXERCICE_DATE, exercice.getDate());
        values.put(DBConstantes.EXERCICE_HEURE_DEBUT, exercice.getHeure_debut());
        values.put(DBConstantes.EXERCICE_HEURE_FIN, exercice.getHeure_fin());

        return bdd.insert(DBConstantes.TABLE_EXERCICE, null, values);
    }

    // Méthode de mis à jour
    public long modifierUnExercice(int id, Exercice exercice){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.EXERCICE_ID_UTILISATEUR, exercice.getId_utilisateur());
        values.put(DBConstantes.EXERCICE_DISTANCE, exercice.getDistance());
        values.put(DBConstantes.EXERCICE_TIME, exercice.getTime());
        values.put(DBConstantes.EXERCICE_CALORIES, exercice.getCalories());
        values.put(DBConstantes.EXERCICE_TYPE_EXERCICE, exercice.getTypeExercice().getId());
        values.put(DBConstantes.EXERCICE_DATE, exercice.getDate());
        values.put(DBConstantes.EXERCICE_HEURE_DEBUT, exercice.getHeure_debut());
        values.put(DBConstantes.EXERCICE_HEURE_FIN, exercice.getHeure_fin());

        return bdd.update(DBConstantes.TABLE_EXERCICE, values, DBConstantes.EXERCICE_ID +" = "+id, null);
    }

    // Méthode de suppression
    public int supprimerUnExercice(int id){
        return bdd.delete(DBConstantes.TABLE_EXERCICE, DBConstantes.EXERCICE_ID +" = "+id, null);
    }

    // Méthode de recherche avec l'identifiant
    public Exercice getExercice(int id){
        Cursor c = bdd.query(DBConstantes.TABLE_EXERCICE,
                new String[]{DBConstantes.EXERCICE_ID,
                        DBConstantes.EXERCICE_ID_UTILISATEUR,
                        DBConstantes.EXERCICE_DISTANCE,
                        DBConstantes.EXERCICE_TIME,
                        DBConstantes.EXERCICE_CALORIES,
                        DBConstantes.EXERCICE_TYPE_EXERCICE,
                        DBConstantes.EXERCICE_DATE,
                        DBConstantes.EXERCICE_HEURE_DEBUT,
                        DBConstantes.EXERCICE_HEURE_FIN},
                DBConstantes.EXERCICE_ID+" = "+id, null, null, null, null);

        return cursorToExercice(c);
    }

    // Méthode de recherche ave l'email et le mot de passe
    public ArrayList<Exercice> getAllUtilisateurExercices(int id_utilisateur){
        Cursor c = bdd.query(DBConstantes.TABLE_EXERCICE,
                new String[]{DBConstantes.EXERCICE_ID,
                        DBConstantes.EXERCICE_ID_UTILISATEUR,
                        DBConstantes.EXERCICE_DISTANCE,
                        DBConstantes.EXERCICE_TIME,
                        DBConstantes.EXERCICE_CALORIES,
                        DBConstantes.EXERCICE_TYPE_EXERCICE,
                        DBConstantes.EXERCICE_DATE,
                        DBConstantes.EXERCICE_HEURE_DEBUT,
                        DBConstantes.EXERCICE_HEURE_FIN},
                DBConstantes.EXERCICE_ID_UTILISATEUR+" = "+id_utilisateur, null, null, null, null);

        return cursorToExercices(c);
    }

    // Méthode de recherche ave l'email et le mot de passe
    public ArrayList<Exercice> getAllDayExercices(int id_utilisateur, String date){
        Cursor c = bdd.query(DBConstantes.TABLE_EXERCICE,
                new String[]{DBConstantes.EXERCICE_ID,
                        DBConstantes.EXERCICE_ID_UTILISATEUR,
                        DBConstantes.EXERCICE_DISTANCE,
                        DBConstantes.EXERCICE_TIME,
                        DBConstantes.EXERCICE_CALORIES,
                        DBConstantes.EXERCICE_TYPE_EXERCICE,
                        DBConstantes.EXERCICE_DATE,
                        DBConstantes.EXERCICE_HEURE_DEBUT,
                        DBConstantes.EXERCICE_HEURE_FIN},
                DBConstantes.EXERCICE_ID_UTILISATEUR+" = "+id_utilisateur+" AND date = \""+date+"\"", null, null, null, null);

        return cursorToExercices(c);
    }

    // Méthode de lecture d'un curseur
    private Exercice cursorToExercice(Cursor c){

        if(c.getCount() == 0)
            return null;

        c.moveToFirst();

        TypeExerciceDAO typeExerciceDAO = new TypeExerciceDAO(context);
        typeExerciceDAO.openR();

        PositionDAO positionDAO = new PositionDAO(context);
        positionDAO.openR();


            Exercice exercice = new Exercice();
            exercice.setId(c.getInt(DBConstantes.EXERCICE_ID_ID));
            exercice.setId_utilisateur(c.getInt(DBConstantes.EXERCICE_ID_UTILISATEUR_ID));
            exercice.setDistance(c.getFloat(DBConstantes.EXERCICE_DISTANCE_ID));
            exercice.setTime(c.getString(DBConstantes.EXERCICE_TIME_ID));
            exercice.setCalories(c.getFloat(DBConstantes.EXERCICE_CALORIES_ID));

            // Récupération du type d'exercice dans un Exercice
            TypeExercice typeExercice = new TypeExercice();
            typeExercice = typeExerciceDAO.getTypeExercice(c.getInt(DBConstantes.EXERCICE_TYPE_EXERCICE_ID));
            exercice.setTypeExercice(typeExercice);


            // Récupération de toutes les positions occupée au cours d'un Exercice
            ArrayList<Position> positions = new ArrayList<Position>();
            positions = positionDAO.getPositions(c.getInt(DBConstantes.EXERCICE_ID_ID));
            exercice.setPositions(positions);

            exercice.setDate(c.getString(DBConstantes.EXERCICE_DATE_ID));
            exercice.setHeure_debut(c.getString(DBConstantes.EXERCICE_HEURE_DEBUT_ID));
            exercice.setHeure_fin(c.getString(DBConstantes.EXERCICE_HEURE_FIN_ID));

        positionDAO.close();

        typeExerciceDAO.close();

        // On ferme le curseur
        c.close();

        // On retourne les informations de l'utilisateur trouvé
        return exercice;
    }


    // Méthode de lecture d'un curseur
    private ArrayList<Exercice> cursorToExercices(Cursor c){

        if(c.getCount() == 0)
            return null;

        ArrayList<Exercice> retours = new ArrayList<>(c.getCount());
        c.moveToFirst();

        TypeExerciceDAO typeExerciceDAO = new TypeExerciceDAO(context);
        typeExerciceDAO.openR();

        PositionDAO positionDAO = new PositionDAO(context);
        positionDAO.openR();

        do{
            Exercice exercice = new Exercice();
            exercice.setId(c.getInt(DBConstantes.EXERCICE_ID_ID));
            exercice.setId_utilisateur(c.getInt(DBConstantes.EXERCICE_ID_UTILISATEUR_ID));
            exercice.setDistance(c.getFloat(DBConstantes.EXERCICE_DISTANCE_ID));
            exercice.setTime(c.getString(DBConstantes.EXERCICE_TIME_ID));
            exercice.setCalories(c.getFloat(DBConstantes.EXERCICE_CALORIES_ID));

            // Récupération du type d'exercice dans un Exercice
            TypeExercice typeExercice = new TypeExercice();
            typeExercice = typeExerciceDAO.getTypeExercice(c.getInt(DBConstantes.EXERCICE_TYPE_EXERCICE_ID));
            exercice.setTypeExercice(typeExercice);


            // Récupération de toutes les positions occupée au cours d'un Exercice
            ArrayList<Position> positions = new ArrayList<Position>();
            positions = positionDAO.getPositions(c.getInt(DBConstantes.EXERCICE_ID_ID));
            exercice.setPositions(positions);

            exercice.setDate(c.getString(DBConstantes.EXERCICE_DATE_ID));
            exercice.setHeure_debut(c.getString(DBConstantes.EXERCICE_HEURE_DEBUT_ID));
            exercice.setHeure_fin(c.getString(DBConstantes.EXERCICE_HEURE_FIN_ID));

            retours.add(exercice);
        }while(c.moveToNext());

        positionDAO.close();

        typeExerciceDAO.close();
        // On ferme le curseur
        c.close();

        // On retourne les informations de l'utilisateur trouvé
        return retours;
    }
}
