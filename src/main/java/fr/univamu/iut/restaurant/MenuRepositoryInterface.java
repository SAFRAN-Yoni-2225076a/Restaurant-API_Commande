package fr.univamu.iut.restaurant;

import java.util.*;

/**
 * Interface d'accès aux données des livres
 */
public interface MenuRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les livres
     */
    public void close();

    /**
     * Méthode retournant le livre dont la référence est passée en paramètre
     * @param id identifiant du livre recherché
     * @return un objet Book représentant le livre recherché
     */
    public Menu getMenu( int id );

    /**
     * Méthode retournant la liste des livres
     * @return une liste d'objets livres
     */
    public ArrayList<Menu> getAllMenu() ;

    /**
     * Méthode permettant de mettre à jours un livre enregistré
     * @param id identifiant du menu à mettre à jours
     * @param author nouvel auteur du menu
     * @param description nouvelle description du menu
     * @param prix nouveau prix du menu
     * @return true si la mise à jours a été effectuée, false sinon
     */
    public boolean updateMenu( int id, String author, String description, double prix);
}