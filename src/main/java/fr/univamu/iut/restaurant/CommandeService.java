package fr.univamu.iut.restaurant;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class CommandeService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les
     * menus
     */
    private CommandeRepositoryInterface commandeRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     *
     * @param commandeRepo objet implémentant l'interface d'accès aux données
     */
    public CommandeService(CommandeRepositoryInterface commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    /**
     * Méthode retournant les informations sur les menus au format JSON
     *
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllCommandeJSON() {
        ArrayList<Commande> allCommandes = commandeRepo.getAllCommande();
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCommandes);
        } catch (Exception e) {
            // Gérer l'erreur correctement, par exemple journaliser l'erreur
            System.err.println("Erreur lors de la conversion en JSON : " + e.getMessage());
            // Retourner une réponse d'erreur appropriée à l'appelant
            return "{\"error\": \"Erreur lors de la conversion en JSON\"}";
        }
        return result;
    }


    /*
     * Méthode retournant au format JSON les informations sur une commande recherché
     *
     * @param adresse adresse de la commande recherché
     * 
     * @return une chaîne de caractère contenant les informations au format JSON
     */

    public String getCommandeJSON(String adresse) throws SQLException, ClassNotFoundException {
        commandeRepo = new CommandeRepositoryMariaDB(
                "jdbc:mariadb://mysql-yonisafran.alwaysdata.net/yonisafran_annonces_db",
                "345222_commande", "Chat_123456789");
        String result = null;
        Commande myCommande = commandeRepo.getCommande(adresse);

        // Si le menu a été trouvé
        if (myCommande != null) {

            // Création du JSON et conversion du menu
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    /**
     * Méthode permettant de mettre à jours les informations d'une commande
     *
     * @param adresse de la commande à mettre à jours
     * @param commande les nouvelles informations à été utiliser
     * @return true si le menu a pu être mis à jours
     */
    public boolean updateCommande(String adresse, Commande commande) {
        return commandeRepo.updateCommande( commande.getMenu(), commande.getLivraison_date(), commande.getPrix(),  adresse);
    }

    public boolean deleteCommande(String adresse){
        return commandeRepo.deleteCommande(adresse);
    }

    public boolean createCommande(Commande commande){
        return commandeRepo.createCommande(commande);
    }
}