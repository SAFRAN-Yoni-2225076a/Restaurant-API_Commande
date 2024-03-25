package fr.univamu.iut.restaurant;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static java.time.LocalTime.now;

/**
 * Classe permettant d'accèder aux Menu stockés dans une base de données Mariadb
 */
public class MenuRepositoryMariadb implements MenuRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     *
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user           chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd            chaîne de caractères contenant le mot de passe à utiliser
     */
    public MenuRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Menu getMenu(int id) {

        Menu selectedMenu = null;

        String query = "SELECT * FROM menu WHERE id=?";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du livre est valide)
            if (result.next()) {
                String description = result.getString("description");
                String authors = result.getString("authors");
                double prix = result.getDouble("prix");

                // création et initialisation de l'objet Book
                selectedMenu = new Menu(authors, description, prix);
                selectedMenu.setCreation_date(now());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedMenu;
    }

    @Override
    public ArrayList<Menu> getAllMenu() {
        ArrayList<Menu> listMenu;

        String query = "SELECT * FROM Menu";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listMenu = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while (result.next()) {
                int id = result.getInt("id");
                String authors = result.getString("authors");
                String description = result.getString("description");
                double prix = result.getInt("status");

                // création du livre courant
                Menu currentMenu = new Menu(authors, description, prix);
                currentMenu.setCreation_date(now());

                listMenu.add(currentMenu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMenu;
    }

    @Override
    public boolean updateMenu(int id, String author, String description, double prix) {
        String query = "UPDATE Menu SET author=?, description=?, prix=? WHERE id=?";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, author);
            ps.setString(2, description);
            ps.setDouble(3, prix);
            ps.setInt(4, id);

            // exécution de la requête
            int result = ps.executeUpdate();

            // vérification de la mise à jour
            if (result == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}