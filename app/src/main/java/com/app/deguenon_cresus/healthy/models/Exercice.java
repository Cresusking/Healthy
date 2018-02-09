/**
 * Cette Classe représente le modèle d'un Exercice
 * @author Crésus DEGUENON
 * @version 1.0
 */
package com.app.deguenon_cresus.healthy.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class Exercice implements Serializable{

    //Identifiant d'un Exercice
    private int id;

    //Identifiant de celui qui a fait l'exercice
    private int id_utilisateur;

    //Distance parcourue au cours de l'Exercice
    private float distance;

    // Temps de l'Exercice
    private String time;

    // Difference d'elevation entre le début de l'exercice et sa fin
    private float calories;

    // Le type de d'exercice effectué
    private TypeExercice typeExercice;

    // Les positions de début à la de fin
    private ArrayList<Position> positions;

    //Date de l'exercice
    private String date;

    // Heure de début
    private String heure_debut;

    // Heure de fin
    private String heure_fin;

    public Exercice() {
        positions = new ArrayList<Position>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public TypeExercice getTypeExercice() {
        return typeExercice;
    }

    public void setTypeExercice(TypeExercice typeExercice) {
        this.typeExercice = typeExercice;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }
}
