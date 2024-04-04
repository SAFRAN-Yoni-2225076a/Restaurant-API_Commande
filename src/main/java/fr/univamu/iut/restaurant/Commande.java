package fr.univamu.iut.restaurant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commande {

    List<Integer> menu;
    Date creation_date;
    Date livraison_date;
    double prix;
    String adresse;

    public Commande(int id, List<Integer> menu, Date livraison_date, double prix, String adresse) {

        this.menu = menu;
        this.livraison_date = livraison_date;
        this.prix = prix;
        this.creation_date = new Date();
        this.adresse = adresse;
    }

    public List<Integer> getMenu() {
        return menu;
    }

    public void setMenu(List<Integer> menu) {
        this.menu = menu;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getLivraison_date() {
        return livraison_date;
    }

    public void setLivraison_date(Date livraison_date) {
        this.livraison_date = livraison_date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "menu=" + menu +
                ", creation_date=" + creation_date +
                ", livraison_date=" + livraison_date +
                ", prix=" + prix +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
