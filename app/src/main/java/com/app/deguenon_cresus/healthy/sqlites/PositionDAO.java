package com.app.deguenon_cresus.healthy.sqlites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.app.deguenon_cresus.healthy.apps.DBConstantes;
import com.app.deguenon_cresus.healthy.models.Position;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class PositionDAO {

    // Declaration d'une base de données SQLiteDatabase
    private SQLiteDatabase bdd;

    // Déclaration de la Classe MaBase
    private MaBase maBase;

    public PositionDAO(Context context){

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
    public long ajouterUnePosition(Position position){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.POSITION_ID_EXERCICE, position.getId_exercice());
        values.put(DBConstantes.POSITION_LAT, position.getLat());
        values.put(DBConstantes.POSITION_LNG, position.getLng());
        values.put(DBConstantes.POSITION_SECONDES, position.getSecondes());

        return bdd.insert(DBConstantes.TABLE_POSITION, null, values);
    }

    // Méthode de recherche avec l'identifiant
    public ArrayList<Position> getPositions(int id_exercice){
        Cursor c = bdd.query(DBConstantes.TABLE_POSITION,
                new String[]{DBConstantes.POSITION_ID,
                        DBConstantes.POSITION_ID_EXERCICE,
                        DBConstantes.POSITION_LAT,
                        DBConstantes.POSITION_LNG,
                        DBConstantes.POSITION_SECONDES},
                DBConstantes.POSITION_ID_EXERCICE+" = "+id_exercice, null, null, null, null);

        return cursorToPositions(c);
    }

    // Méthode de lecture d'un curseur
    private ArrayList<Position> cursorToPositions(Cursor c){

        if(c.getCount() == 0)
            return null;

        ArrayList<Position> retours = new ArrayList<>(c.getCount());
        c.moveToFirst();

        do{
            Position position = new Position();
            position.setId(c.getInt(DBConstantes.POSITION_ID_ID));
            position.setId_exercice(c.getInt(DBConstantes.POSITION_ID_EXERCICE_ID));
            position.setLat(c.getDouble(DBConstantes.POSITION_LAT_ID));
            position.setLng(c.getDouble(DBConstantes.POSITION_LNG_ID));
            position.setSecondes(c.getLong(DBConstantes.POSITION_SECONDES_ID));

            retours.add(position);
        }while(c.moveToNext());

        // On ferme le curseur
        c.close();

        // On retourne les informations de l'Position trouvé
        return retours;
    }
}
