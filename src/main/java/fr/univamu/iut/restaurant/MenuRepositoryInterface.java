package fr.univamu.iut.restaurant;

import java.util.*;

/**
 * Interface d'accès aux données des livres
 */
public interface MenuRepositoryInterface {

    Commande getMenu(int id);

    ArrayList<Commande> getAllMenu();

    boolean updateMenu(int id, String author, String description, double prix);

    boolean deleteMenu(int id);

    boolean createMenu(Commande commande);

    void close();
}