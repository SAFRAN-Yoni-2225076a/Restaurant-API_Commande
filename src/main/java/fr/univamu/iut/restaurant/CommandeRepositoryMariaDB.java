package fr.univamu.iut.restaurant;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeRepositoryMariaDB implements CommandeRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public CommandeRepositoryMariaDB(String infoConnection, String user, String pwd)
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
    public Commande getCommande(String adresse) {

        Commande selectedCommande = null;

        String query = "SELECT * FROM commande WHERE adresse=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, adresse);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String menu = result.getString("menu");
                Date livraison_date = result.getDate("dateLiv");
                double prix = result.getDouble("prix");

                selectedCommande = new Commande(menu, livraison_date, prix, adresse);
                selectedCommande.setCreation_date(new Date());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedCommande;
    }

    @Override
    public ArrayList<Commande> getAllCommande() {
        ArrayList<Commande> listCommandes;

        String query = "SELECT * FROM Commande";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listCommandes = new ArrayList<>();

            while (result.next()) {
                String menu = result.getString("menu");
                Date livraison_date = result.getDate("dateLiv");
                double prix = result.getDouble("prix");
                String adresse = result.getString("adresse");

                Commande currentCommande = new Commande(menu, livraison_date, prix, adresse);
                currentCommande.setCreation_date(new Date());

                listCommandes.add(currentCommande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCommandes;
    }


    @Override
    public boolean updateCommande(String menu, Date livraison_date, double prix, String adresse) {

        String query = "UPDATE Commande SET menu=?, dateLiv=?, prix=? WHERE adresse=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1,  menu);
            ps.setDate(2, (java.sql.Date) livraison_date);
            ps.setDouble(3, prix);

            int result = ps.executeUpdate();

            Commande currentCommande = getCommande(adresse);
            currentCommande.setCreation_date(new Date());

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCommande(String adresse) {

        String query = "DELETE FROM Commande WHERE adresse=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, adresse);

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createCommande(Commande commande) {

        String query = "INSERT INTO Commande (menu, dateliv, prix, adresse) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, commande.getMenu());
            ps.setDate(2, (java.sql.Date) commande.getLivraison_date());
            ps.setDouble(3, commande.getPrix());
            ps.setString(4, commande.getAdresse());

            int result = ps.executeUpdate();

            return result == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}