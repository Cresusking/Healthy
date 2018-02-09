/**
 * Cette Classe représente le modèle d'un type d'exercice effectué par un utilisateur
 * @author Crésus DEGUENON
 * @version 1.0
 */
package com.app.deguenon_cresus.healthy.models;

import java.io.Serializable;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class TypeExercice implements Serializable{

    // Identifiant du Type d'Exercice
    private int id;

    // Resources icon
    private int icon;

    // L'intitulé du type d'exercice
    private String nom;

    public TypeExercice(){

    }

    public TypeExercice(int icon, String nom) {
        this.icon = icon;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "TypeExercice{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
