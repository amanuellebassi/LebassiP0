package dev.lebassi.controllers;

import com.google.gson.Gson;
import dev.lebassi.daos.ClientDaoPostgressDb;
import dev.lebassi.entities.Client;
import dev.lebassi.services.ClientService;
import dev.lebassi.services.ClientServiceImpl;
import io.javalin.http.Handler;

import java.util.Set;

public class ClientController {

 //   private ClientService clientService = new ClientServiceImpl(new ClientDaoLocal());  // change it to new ClientDaoPostgres()
    private ClientService clientService = new ClientServiceImpl(new ClientDaoPostgressDb());  // change it to new ClientDaoPostgres()

    // POST /clients (the body will contain a JSON with the client information)
    // POST /clients => Creates a new client
    // return a 201 status code
    public Handler createClientHandler = (ctx) -> {
        String body = ctx.body();
        Gson gson = new Gson();
        Client client = gson.fromJson(body, Client.class);    // turn that Client JSON into a Java Client Object
        this.clientService.registerClient(client);
        ctx.result("You created a new account!!");
        ctx.status(201);
    };


    // GET  /clients  => gets all clients
    // return a 200 status code
    public Handler getAllClientHandler = (ctx) ->  {
        String titleContains = ctx.queryParam("titleContains", "NONE");

        if (titleContains.equals("NONE"))   {
            Set<Client> allClients =  this.clientService.getAllClients();
            Gson gson = new Gson();
            String clientJSON = gson.toJson(allClients);
            ctx.result(clientJSON);
            ctx.status(200);
        } else {
            Set<Client> clients = this.clientService.getAllClients();
            // Write the else statement
        }
    };

    // GET /client/:id
    // GET /clients/10 => get client with id of 10
    // return 404 if no such client exist
    public Handler getClientByIdHandler= (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Client client = this.clientService.getClientById(id);
        if(client == null) {
            ctx.result("Client not found");
            ctx.status(404);
        }  else {
            Gson gson = new Gson();
            String clientJSON = gson.toJson(client);
            ctx.result(clientJSON);
            ctx.status(200);
        }

    };

    // PUT /clients/12 => updates client number 12
    public Handler updateClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Client client = this.clientService.getClientById(id);
        if(client == null) {
            ctx.result("Such client does not exist");
            ctx.status(404);
        }  else {
            String body = ctx.body();
            Gson gson = new Gson();
            Client clt = gson.fromJson(body, Client.class);
            clt.setClientId(id);
            this.clientService.updateClient(clt);
        }
    };

    // DELETE /clients/15 => deletes client with the id of15
    // return 404 if no such client exist
    public Handler deleteClientHandler = (ctx) -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean deleted = this.clientService.deleteClientById(id);
        System.out.println("Hey " +  this.clientService.getClientById(id));

        if(deleted ) {
            ctx.result("Client with id: " + id + " was deleted");
        } else  {
            ctx.result("Oh no, could not delete, because Client with id: "+ id + " does not exist");
            ctx.status(404);
        }

    };

}
