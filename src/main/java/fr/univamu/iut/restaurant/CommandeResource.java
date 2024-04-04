package fr.univamu.iut.restaurant;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

/**
 * Ressource associée aux Menus
 * (point d'accès de l'API REST)
 */
@Path("/commande")
public class CommandeResource {

    /**
     * Service utilisé pour accéder aux données des Menus et récupérer/modifier
     * leurs informations
     */
    private CommandeService service;

    /**
     * Constructeur par défaut
     */
    public CommandeResource() {
    }

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès
     * aux données
     * 
     * @param commandeRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject CommandeResource(CommandeRepositoryInterface commandeRepo) {
        this.service = new CommandeService(commandeRepo);
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux Menus
     */
    public CommandeResource(CommandeService service) {
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les Menus enregistrés
     * 
     * @return la liste des Menus (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllCommande() {
        return service.getAllCommandeJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un Menu dont la référence
     * est passée paramètre dans le chemin
     * 
     * @param adresse référence du Menu recherché
     * @return les informations du Menu recherché au format JSON
     */
    @GET
    @Path("{adresse}")
    @Produces("application/json")
    public String getCommande(@PathParam("adresse") String adresse) throws SQLException, ClassNotFoundException {

        String result = service.getCommandeJSON(adresse);

        // si la commande n'a pas été trouvée
        if (result == null)
            throw new NotFoundException();

        return result;
    }

    @GET
    @Path("/test")
    @Produces("application/json")
    public String getTest() {


        return "test";
    }

    /**
     * Endpoint permettant de mettre à jours le statut d'une commande uniquement
     * (la requête patch doit fournir le nouveau statut sur Commande, les autres
     * informations sont ignorées)
     * 
     * @param adresse   adresse du Menu dont il faut changer le statut
     * @param commande la commandetransmis en HTTP au format JSON et convertit en
     *             objet Commande
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur
     *         NotFound sinon
     */
    @PUT
    @Path("{adresse}")
    @Consumes("application/json")
    public Response updateMenu(@PathParam("adresse") String adresse, Commande commande) {

        // si la commande n'a pas été trouvée
        if (!service.updateCommande(adresse, commande))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }
}