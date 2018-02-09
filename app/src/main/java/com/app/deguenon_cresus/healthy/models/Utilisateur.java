/**
 * Cette Classe représente le modèle des informations récupérée chez un utilisateur
 * @author Crésus DEGUENON
 * @version 1.0
 */
package com.app.deguenon_cresus.healthy.models;

/**
 * Created by c.deguenon.15 on 07/11/2017.
 */

public class Utilisateur {

    // identifiant d'un Utilisateur
    private int id;

    // Son nom de famille
    private String nom;

    // Son ou ses prénoms
    private String prenom;

    // Son adresse email
    private String email;

    // Son mot de passe
    private String password;

    // Son poids
    private int poids;

    // L'age de l'utilisateur
    private int age;

    // Représente le rythme cardiaque d'un Utilisateur
    private int rythme_cardiaque;

    // Le genre de l'utilisateur
    private int genre;


    public Utilisateur() {

    }

    public Utilisateur(int id, String nom, String prenom, String email, String password, int poids, int age, int rythme_cardiaque, int genre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.poids = poids;
        this.age = age;
        this.rythme_cardiaque = rythme_cardiaque;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRythme_cardiaque() {
        return rythme_cardiaque;
    }

    public void setRythme_cardiaque(int rythme_cardiaque) {
        this.rythme_cardiaque = rythme_cardiaque;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", poids=" + poids +
                ", age=" + age +
                ", rythme_cardiaque=" + rythme_cardiaque +
                ", genre='" + genre + '\'' +
                '}';
    }
}
