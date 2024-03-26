package fr.univamu.iut.restaurant;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class MenuService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les
     * menus
     */
    protected MenuRepositoryInterface menuRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     *
     * @param menuRepo objet implémentant l'interface d'accès aux données
     */
    public MenuService(MenuRepositoryInterface menuRepo) {
        this.menuRepo = menuRepo;
    }

    /**
     * Méthode retournant les informations sur les menus au format JSON
     *
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllMenusJSON() {

        ArrayList<Menu> allMenus = menuRepo.getAllMenu();

        // Création du JSON et conversion de la liste de menus
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allMenus);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un menu recherché
     *
     * @param id id du menu recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getMenuJSON(int id) {

        String result = null;
        Menu myMenu = menuRepo.getMenu(id);

        // Si le menu a été trouvé
        if (myMenu != null) {

            // Création du JSON et conversion du menu
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myMenu);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    /**
     * Méthode permettant de mettre à jours les informations d'un menu
     *
     * @param id   id du menu à mettre à jours
     * @param menu les nouvelles informations à été utiliser
     * @return true si le menu a pu être mis à jours
     */
    public boolean updateMenu(int id, Menu menu) {
        return menuRepo.updateMenu(id, menu.getAuthor(), menu.getDescription(), menu.getPrix());
    }
}