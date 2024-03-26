package fr.univamu.iut.restaurant;

import java.util.*;

/**
 * Interface d'accès aux données des livres
 */
public interface MenuRepositoryInterface {

    Menu getMenu(int id);

    ArrayList<Menu> getAllMenu();

    boolean updateMenu(int id, String author, String description, double prix);

    boolean deleteMenu(int id);

    boolean createMenu(Menu menu);

    void close();
}