/**
 * Cette Classe aide pour les opérations  géneral de la base de données (Création des tables et Mis à jour de la base de données)
 * @author Crésus DEGUENON
 * @version 1.0
 */
package com.app.deguenon_cresus.healthy.sqlites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.deguenon_cresus.healthy.apps.DBConstantes;


/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class MaBase extends SQLiteOpenHelper {


    public MaBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstantes.CREATE_TABLE_UTILISATEUR);
        db.execSQL(DBConstantes.CREATE_TABLE_EXERCICE);
        db.execSQL(DBConstantes.CREATE_TABLE_POSITION);
        db.execSQL(DBConstantes.CREATE_TABLE_TYPE_EXERCICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DBConstantes.DROP_TABLE_UTILISATEUR);
        db.execSQL(DBConstantes.DROP_TABLE_EXERCICE);
        db.execSQL(DBConstantes.DROP_TABLE_POSITION);
        db.execSQL(DBConstantes.DROP_TABLE_TYPE_EXERCICE);

        onCreate(db);
    }
}
