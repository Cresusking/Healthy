/**
 * Cette Classe représente le modèle de la position de l'utilisateur à un moment donné
 * @author Crésus DEGUENON
 * @version 1.0
 */
package com.app.deguenon_cresus.healthy.models;

import java.io.Serializable;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class Position implements Serializable{
    // Identifiant de la Position
    private int id;

    //Identifiant de l'exercice auquel il est relié
    private int id_exercice;

    //Latitude de la Position
    private double lat;

    //Longitude le la Position
    private double lng;

    // Le temps effectué à cette position en seconde
    private long secondes;

    public Position() {

    }

    public Position(int id_exercice, double lat, double lng, long secondes) {
        this.id_exercice = id_exercice;
        this.lat = lat;
        this.lng = lng;
        this.secondes = secondes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_exercice() {
        return id_exercice;
    }

    public void setId_exercice(int id_exercice) {
        this.id_exercice = id_exercice;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getSecondes() {
        return secondes;
    }

    public void setSecondes(long secondes) {
        this.secondes = secondes;
    }
}
