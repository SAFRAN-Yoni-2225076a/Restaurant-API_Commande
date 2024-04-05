package fr.univamu.iut.restaurant;

import java.util.*;

/**
 * Interface d'accès aux données des livres
 */
public interface CommandeRepositoryInterface {

    Commande getCommande(String adresse);

    ArrayList<Commande> getAllCommande();

    boolean updateCommande(String menu, Date livraison_date, double prix, String adresse);

    boolean deleteCommande(String adresse);

    boolean createCommande(Commande commande);

    void close();
}