package com.app.deguenon_cresus.healthy.apps;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class DBConstantes {
    /* Information sur la base de donnée */
    public final static String BD_NOM = "mini_projet.db";
    public final static int VERSION = 1;

    /* Informations de la table utilisateur */
    public final static String TABLE_UTILISATEUR = "utilisateur";

    public final static String UTILISATEUR_ID = "id";
    public final static int UTILISATEUR_ID_ID = 0;
    public final static String UTILISATEUR_NOM = "nom";
    public final static int UTILISATEUR_NOM_ID = 1;
    public final static String UTILISATEUR_PRENOM = "prenom";
    public final static int UTILISATEUR_PRENOM_ID = 2;
    public final static String UTILISATEUR_EMAIL = "email";
    public final static int UTILISATEUR_EMAIL_ID = 3;
    public final static String UTILISATEUR_PASSWORD = "password";
    public final static int  UTILISATEUR_PASSWORD_ID = 4;
    public final static String UTILISATEUR_POIDS = "poids";
    public final static int  UTILISATEUR_POIDS_ID = 5;
    public final static String UTILISATEUR_AGE = "age";
    public final static int  UTILISATEUR_AGE_ID = 6;
    public final static String UTILISATEUR_RYTHME_CARDIAQUE = "rythme_cardiaque";
    public final static int  UTILISATEUR_RYTHME_CARDIAQUE_ID = 7;
    public final static String UTILISATEUR_GENRE = "genre";
    public final static int  UTILISATEUR_GENRE_ID = 8;

    public final static String CREATE_TABLE_UTILISATEUR = "CREATE TABLE "+TABLE_UTILISATEUR+"( "
            +UTILISATEUR_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +UTILISATEUR_NOM+" TEXT NOT NULL, "
            +UTILISATEUR_PRENOM+" TEXT NOT NULL, "
            +UTILISATEUR_EMAIL+" TEXT NOT NULL, "
            +UTILISATEUR_PASSWORD+" TEXT NOT NULL, "
            +UTILISATEUR_POIDS+" TEXT NOT NULL, "
            +UTILISATEUR_AGE+" TEXT NOT NULL, "
            +UTILISATEUR_RYTHME_CARDIAQUE+" TEXT NOT NULL, "
            +UTILISATEUR_GENRE+" TEXT NOT NULL);";

    public final static String DROP_TABLE_UTILISATEUR = "DROP TABLE IF EXISTS "+TABLE_UTILISATEUR;

    /* Informations de la table exercice */
    public final static String TABLE_EXERCICE = "exercice";

    public final static String EXERCICE_ID = "id";
    public final static int EXERCICE_ID_ID = 0;
    public final static String EXERCICE_ID_UTILISATEUR = "id_utilisateur";
    public final static int EXERCICE_ID_UTILISATEUR_ID = 1;
    public final static String EXERCICE_DISTANCE = "distance";
    public final static int EXERCICE_DISTANCE_ID = 2;
    public final static String EXERCICE_TIME = "time";
    public final static int EXERCICE_TIME_ID = 3;
    public final static String EXERCICE_CALORIES = "calories";
    public final static int EXERCICE_CALORIES_ID = 4;
    public final static String EXERCICE_TYPE_EXERCICE = "type_exercice";
    public final static int EXERCICE_TYPE_EXERCICE_ID = 5;
    public final static String EXERCICE_DATE = "date";
    public final static int  EXERCICE_DATE_ID = 6;
    public final static String EXERCICE_HEURE_DEBUT = "heure_debut";
    public final static int  EXERCICE_HEURE_DEBUT_ID = 7;
    public final static String EXERCICE_HEURE_FIN = "heure_fin";
    public final static int  EXERCICE_HEURE_FIN_ID = 8;

    public final static String CREATE_TABLE_EXERCICE = "CREATE TABLE "+TABLE_EXERCICE+"( "
            +EXERCICE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +EXERCICE_ID_UTILISATEUR+" TEXT NOT NULL, "
            +EXERCICE_DISTANCE+" TEXT NOT NULL, "
            +EXERCICE_TIME+" TEXT NOT NULL, "
            +EXERCICE_CALORIES+" TEXT NOT NULL, "
            +EXERCICE_TYPE_EXERCICE+" TEXT NOT NULL, "
            +EXERCICE_DATE+" TEXT NOT NULL, "
            +EXERCICE_HEURE_DEBUT+" TEXT NOT NULL, "
            +EXERCICE_HEURE_FIN+" TEXT NOT NULL);";

    public final static String DROP_TABLE_EXERCICE = "DROP TABLE IF EXISTS "+TABLE_EXERCICE;

    /* Informations de la table position */
    public final static String TABLE_TYPE_EXERCICE  = "type_exercice";

    public final static String TYPE_EXERCICE_ID = "id";
    public final static int TYPE_EXERCICE_ID_ID = 0;
    public final static String TYPE_EXERCICE_ICON = "icon";
    public final static int TYPE_EXERCICE_ICON_ID = 1;
    public final static String TYPE_EXERCICE_NOM = "nom";
    public final static int TYPE_EXERCICE_NOM_ID = 2;

    public final static String CREATE_TABLE_TYPE_EXERCICE = "CREATE TABLE "+TABLE_TYPE_EXERCICE+"( "
            +TYPE_EXERCICE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TYPE_EXERCICE_ICON+" TEXT NOT NULL, "
            +TYPE_EXERCICE_NOM+" TEXT NOT NULL);";

    public final static String DROP_TABLE_TYPE_EXERCICE = "DROP TABLE IF EXISTS "+TABLE_TYPE_EXERCICE;

    /* Informations de la table position */
    public final static String TABLE_POSITION = "position";

    public final static String POSITION_ID = "id";
    public final static int POSITION_ID_ID = 0;
    public final static String POSITION_ID_EXERCICE = "id_exercice";
    public final static int POSITION_ID_EXERCICE_ID = 1;
    public final static String POSITION_LAT = "lat";
    public final static int POSITION_LAT_ID = 2;
    public final static String POSITION_LNG = "lng";
    public final static int POSITION_LNG_ID = 3;
    public final static String POSITION_SECONDES = "vitesse";
    public final static int POSITION_SECONDES_ID = 4;

    public final static String CREATE_TABLE_POSITION = "CREATE TABLE "+TABLE_POSITION+"( "
            +POSITION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +POSITION_ID_EXERCICE+" TEXT NOT NULL, "
            +POSITION_LAT+" TEXT NOT NULL, "
            +POSITION_LNG+" TEXT NOT NULL, "
            +POSITION_SECONDES+" TEXT NOT NULL);";

    public final static String DROP_TABLE_POSITION = "DROP TABLE IF EXISTS "+TABLE_POSITION;


}
