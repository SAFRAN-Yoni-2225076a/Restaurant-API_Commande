package fr.univamu.iut.restaurant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class HelloApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de
     * données au moment de la création
     * de la ressource
     *
     * @return un objet implémentant l'interface MenuRepositoryInterface utilisée
     *         pour accéder aux données des livres, voire les modifier
     */
    @Produces
    private CommandeRepositoryInterface openDbConnection() {

        CommandeRepositoryMariaDB db = null;

        try {
            db = new CommandeRepositoryMariaDB(
                    "jdbc:mariadb://mysql-archilogicieltibo.alwaysdata.net/archilogicieltibo_restaurantmenu",
                    "345226_menu", "Deliveroo$");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return db;
    }



    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque
     * l'application est arrêtée
     *
     * @param menuRepo la connexion à la base de données instanciée dans la
     *                 méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes CommandeRepositoryInterface menuRepo) {

        menuRepo.close();
    }
}