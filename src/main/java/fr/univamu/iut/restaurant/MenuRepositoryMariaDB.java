package fr.univamu.iut.restaurant;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class MenuRepositoryMariaDB implements MenuRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public MenuRepositoryMariaDB(String infoConnection, String user, String pwd)
            throws java.sql.SQLException, java.lang.ClassNotFoundException {
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
    public Commande getMenu(int id) {

        Commande selectedCommande = null;

        String query = "SELECT * FROM menu WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String description = result.getString("description");
                String author = result.getString("author");
                double prix = result.getDouble("prix");

                selectedCommande = new Commande(id, author, description, prix);
                selectedCommande.setCreation_date(new Date());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedCommande;
    }

    @Override
    public ArrayList<Commande> getAllMenu() {
        ArrayList<Commande> listCommandes;

        String query = "SELECT * FROM Menu";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listCommandes = new ArrayList<>();

            while (result.next()) {
                int id = result.getInt("id");
                String author = result.getString("author");
                String description = result.getString("description");
                double prix = result.getInt("prix");

                Commande currentCommande = new Commande(id, author, description, prix);
                currentCommande.setCreation_date(new Date());

                listCommandes.add(currentCommande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCommandes;
    }

    @Override
    public boolean updateMenu(int id, String author, String description, double prix) {

        String query = "UPDATE Menu SET author=?, description=?, prix=? WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, author);
            ps.setString(2, description);
            ps.setDouble(3, prix);

            int result = ps.executeUpdate();

            Commande currentCommande = getMenu(id);
            currentCommande.setCreation_date(new Date());

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMenu(int id) {

        String query = "DELETE FROM Menu WHERE id=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createMenu(Commande commande) {

        String query = "INSERT INTO Menu (author, description, prix) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, commande.getMenu());
            ps.setString(2, commande.getDescription());
            ps.setDouble(3, commande.getPrix());

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}