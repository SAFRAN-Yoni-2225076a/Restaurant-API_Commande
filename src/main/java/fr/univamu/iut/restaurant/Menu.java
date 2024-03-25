package fr.univamu.iut.restaurant;

import java.time.LocalTime;
import java.util.Date;

public class Menu {

    int id;
    String author;
    String description;

    Date creation_date;
    double prix;


    public Menu(String author, String description, double prix) {
        this.author = author;
        this.description = description;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                '}';
    }

}
