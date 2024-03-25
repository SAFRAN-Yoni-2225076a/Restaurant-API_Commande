package fr.univamu.iut.restaurant;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/menus")
public class APIMenu {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Menu> getAllMenus() {

        // Créer une instance de la classe DAO pour les menus
        MenuDataAccess menuDAO = new MenuDataAccess();

        List<Menu> menus = menuDAO.findAll();

        // Retourner la liste des menus
        return menus;
    }

}
