package com.app.deguenon_cresus.healthy.models;

import java.io.Serializable;

/**
 * Created by c.deguenon.15 on 17/11/2017.
 */

public class CarateristiqueExercice implements Serializable {

    private int id;

    private int icon;

    private String titre;

    private String valeur;

    public CarateristiqueExercice() {
    }

    public CarateristiqueExercice(int icon, String titre, String valeur) {
        this.icon = icon;
        this.titre = titre;
        this.valeur = valeur;
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
