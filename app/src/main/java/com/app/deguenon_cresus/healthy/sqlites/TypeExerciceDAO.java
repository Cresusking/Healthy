package com.app.deguenon_cresus.healthy.sqlites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.app.deguenon_cresus.healthy.apps.DBConstantes;
import com.app.deguenon_cresus.healthy.models.TypeExercice;

import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class TypeExerciceDAO {
    // Declaration d'une base de données SQLiteDatabase
    private SQLiteDatabase bdd;

    // Déclaration de la Classe MaBase
    private MaBase maBase;

    public TypeExerciceDAO(Context context){

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
    public long ajouterUnTypeExercice(TypeExercice typeExercice){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.TYPE_EXERCICE_ICON, typeExercice.getIcon());
        values.put(DBConstantes.TYPE_EXERCICE_NOM, typeExercice.getNom());

        return bdd.insert(DBConstantes.TABLE_TYPE_EXERCICE, null, values);
    }

    // Méthode de mis à jour
    public long modifierUnTypeExercice(int id, TypeExercice typeExercice){
        ContentValues values =  new ContentValues();
        values.put(DBConstantes.TYPE_EXERCICE_ICON, typeExercice.getIcon());
        values.put(DBConstantes.TYPE_EXERCICE_NOM, typeExercice.getNom());

        return bdd.update(DBConstantes.TABLE_TYPE_EXERCICE, values, DBConstantes.TYPE_EXERCICE_ID +" = "+id, null);
    }

    // Méthode de suppression
    public int supprimerUnTypeExercice(int id){
        return bdd.delete(DBConstantes.TABLE_TYPE_EXERCICE, DBConstantes.TYPE_EXERCICE_ID +" = "+id, null);
    }

    // Méthode de recherche avec l'identifiant
    public TypeExercice getTypeExercice(int id){
        Cursor c = bdd.query(DBConstantes.TABLE_TYPE_EXERCICE,
                new String[]{DBConstantes.TYPE_EXERCICE_ID,
                        DBConstantes.TYPE_EXERCICE_ICON,
                        DBConstantes.TYPE_EXERCICE_NOM},
                DBConstantes.TYPE_EXERCICE_ID+" = "+id, null, null, null, null);

        return cursorToTypeExercice(c);
    }

    // Méthode de récupératoin de tous les éléments
    public ArrayList<TypeExercice> getAllTypeExercices(){
        Cursor c = bdd.query(DBConstantes.TABLE_TYPE_EXERCICE,
                new String[]{DBConstantes.TYPE_EXERCICE_ID,
                        DBConstantes.TYPE_EXERCICE_ICON,
                        DBConstantes.TYPE_EXERCICE_NOM}, null, null, null, null, null);

        return cursorToTypeExercices(c);
    }

    // Méthode de lecture d'un curseur
    private TypeExercice cursorToTypeExercice(Cursor c){

        if(c.getCount() == 0)
            return null;

        c.moveToFirst();

        TypeExercice typeExercice = new TypeExercice();
        typeExercice.setId(c.getInt(DBConstantes.TYPE_EXERCICE_ID_ID));
        typeExercice.setIcon(c.getInt(DBConstantes.TYPE_EXERCICE_ICON_ID));
        typeExercice.setNom(c.getString(DBConstantes.TYPE_EXERCICE_NOM_ID));

        // On ferme le curseur
        c.close();

        // On retourne les informations de l'utilisateur trouvé
        return typeExercice;
    }

    // Méthode de lecture d'un curseur
    private ArrayList<TypeExercice> cursorToTypeExercices(Cursor c){

        if(c.getCount() == 0)
            return null;

        ArrayList<TypeExercice> retours = new ArrayList<>(c.getCount());
        c.moveToFirst();

        do{
            TypeExercice typeExercice = new TypeExercice();
            typeExercice.setId(c.getInt(DBConstantes.TYPE_EXERCICE_ID_ID));
            typeExercice.setIcon(c.getInt(DBConstantes.TYPE_EXERCICE_ICON_ID));
            typeExercice.setNom(c.getString(DBConstantes.TYPE_EXERCICE_NOM_ID));

            retours.add(typeExercice);
        }while(c.moveToNext());


        // On ferme le curseur
        c.close();

        // On retourne les informations de l'utilisateur trouvé
        return retours;
    }
}
