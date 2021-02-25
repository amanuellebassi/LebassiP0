package dev.lebassi.app;

import dev.lebassi.controllers.AccountController;
import dev.lebassi.controllers.ClientController;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {

        Javalin app = Javalin.create();

        ClientController client = new ClientController();
        AccountController account = new AccountController();

        // POST /clients => create a new client
        // return a 201 status code
        app.post("/clients", client.createClientHandler);

        // GET /clients => return all clients
        // GET /clients?titleContains=adventures => only returns clients containing that title
        // return a 200 status code
        app.get("/clients", client.getAllClientHandler);

        // GET /client/10 => GET client with ID 10
        app.get("/clients/:id", client.getClientByIdHandler);

        // GET /client/10/accounts => GET all accounts of client with ID 10
        // GET  /clients/:id/accounts/:id2
        app.get("/clients/:id/accounts", account.getAllAccountHandler);

        // GET  /clients/:id/accounts/:id2
        app.get("/clients/:id1/accounts/:id2", account.getAccountByIDHandler);


        //  POST /clients/:id/accounts
        // POST /clients/5/accounts =>creates a new account for client with the id of 5
        // return a 201 status code
        app.post("/clients/:id/accounts", account.postAccountForClientIDHandler);

        // PUT /clients/12 => Update client with ID 12
        app.put("/clients/:id", client.updateClientHandler);

        // DELETE /clients/15 => deletes client with the id of15
        //  return 404 if no such client exist
        app.delete("/clients/:id", client.deleteClientHandler);



        // PUT /clients/10/accounts/3 => update account  with the id 3 for client 10
        // return 404 if no account or client exists
        app.put("/clients/:id1/accounts/:id2", account.updateAccountHandler);

        // GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and 2000
        //		return 404 if no client exists
        app.get("/clients/:id1/accounts?amountLessThan=2000&amountGreaterThan400", account.getAccountByBalanceHandler);

        // DELETE /clients/15/accounts/6 => delete account 6 for client 15
        // return 404 if no account or client exists
        app.delete("/clients/:id1/accounts/:id2", account.deleteAccountByIDHandler);

        app.start(7001);        // actually starts the web server
    }
}
