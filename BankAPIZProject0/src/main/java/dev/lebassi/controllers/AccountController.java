package dev.lebassi.controllers;


import com.google.gson.Gson;
import dev.lebassi.daos.AccountDaoPostgressDb;
import dev.lebassi.entities.Account;
import dev.lebassi.entities.Client;
import dev.lebassi.services.AccountService;
import dev.lebassi.services.AccountServiceImpl;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class AccountController {

    private AccountService accountService = new AccountServiceImpl(new AccountDaoPostgressDb());   // changed it to new AccountDaoPostgres() from AccountDaoLocal
    private static Logger logger = Logger.getLogger(AccountController.class.getName());
    // static Gson gson = new Gson();


    //  GET /clients/7/accounts => get all accounts for client 7
    //	return 404 if no client exists
    public Handler getAllAccountHandler = (ctx) -> {

        String accountContains = ctx.queryParam("accountContains", "NONE");
        if (accountContains.equals("NONE")) {
            int clientId = Integer.parseInt(ctx.pathParam("id"));
            Set<Account> allAccounts = this.accountService.getAccountsByClientId(clientId);
            Gson gson = new Gson();
            String accountJSON = gson.toJson(allAccounts);
            ctx.result(accountJSON);
            ctx.status(200);
        } else {
            int clientId = Integer.parseInt(ctx.pathParam("id"));
            Set<Account> accounts = this.accountService.getAccountsByClientId(clientId);
            Gson gson = new Gson();
            String selectedAccountsJSON = gson.toJson(accounts);
            ctx.result(selectedAccountsJSON);
            ctx.status(200);
        }

    };

    // GET  /clients/:id/accounts/:id2
    public Handler getAccountByIDHandler = (ctx) -> {
  //      int id = Integer.parseInt(ctx.pathParam("id1"));
        int id1 = Integer.parseInt(ctx.pathParam("id1"));
        int id2 = Integer.parseInt(ctx.pathParam("id2"));
        Account account = this.accountService.getAccountByIds(id1, id2);
 //       Account account = (Account) this.accountService.getAccountsByClientId(id);         //getAccountById(id);
        if (account == null) {
            ctx.result("Account not found");
            ctx.status(404);
        } else {
            Gson gson = new Gson();
            String accountJSON = gson.toJson(account);
            ctx.result(accountJSON);
            ctx.status(200);
        }
    };


    //  POST /clients/:id/accounts
    // POST /clients/5/accounts =>creates a new account for client with the id of 5
    //  return a 201 status code
    public Handler postAccountForClientIDHandler = (ctx) -> {
        String body = ctx.body();   // all line 1 does is read in the input from user. so in postman when you send a request, you can send it a body
        Gson gson = new Gson();      // Create a Gson object. It is a reusable object.
        // Step 3 − Deserialize JSON to Object
        // Use fromJson() method to get the Object from the JSON. Pass Json string / source of Json string and object type as parameter.
        Account account = gson.fromJson(body, Account.class);    // line 3 makes it an actual account object ------> turn that Account JSON into a Java Account Object
        this.accountService.registerAccount(account);            // line 4 processes it with the service (so that it gets added into the database)
        ctx.result("You created a new account!! " + body);
        ctx.status(201);
    };

    //DELETE /clients/15/accounts/6 => delete account 6 for client 15
    // return 404 if no account or client exists
    public Handler deleteAccountByIDHandler = (ctx) -> {
        int clientId = Integer.parseInt(ctx.pathParam("id1"));
        int accountId = Integer.parseInt(ctx.pathParam("id2"));
        boolean deleted = this.accountService.deleteAccountByIds(clientId, accountId);           // deleteAccountById(id);
        System.out.println("Hey " + this.accountService.getAccountByIds(clientId, accountId));

        if (deleted) {
            ctx.result("Account with id: " + accountId + " was deleted");
        } else {
            ctx.result("Oh no, could not delete, because Account with id: " + accountId + " does not exist");
            ctx.status(404);
        }

    };

    // PUT /clients/10/accounts/3 => update account  with the id 3 for client 10
    //		return 404 if no account or client exists
    public Handler updateAccountHandler = (ctx) -> {
        int id1 = Integer.parseInt(ctx.pathParam("id1"));
        int id2 = Integer.parseInt(ctx.pathParam("id2"));
        Account account = this.accountService.getAccountByIds(id1, id2);                      // getAccountById(id);
        System.out.println("");
        if (account == null) {
            ctx.result("Such account or client does not exist");
            ctx.status(404);
        } else {
            String body = ctx.body();
            Gson gson = new Gson();
            Account act = gson.fromJson(body, Account.class);
            //       act.setAccountId(id);
            this.accountService.updateAccount(act);
            ctx.result("Updated account successfully");
        }
    };




    public Handler getAccountByBalanceHandler = (ctx) -> {
        int clientId = Integer.parseInt(ctx.pathParam("id"));
        float amountLessThan = Float.parseFloat(ctx.queryParam("amountLessThan", "-1"));
        System.out.println(amountLessThan);
        float amountGreaterThan = Float.parseFloat(ctx.queryParam("amountGreaterThan", "-1"));
        System.out.println(amountGreaterThan);
        Set<Account> allAccountsFromClient;

        if (amountLessThan != -1 && amountGreaterThan != -1){
            allAccountsFromClient = this.accountService.getAccountsByClientId(clientId, amountGreaterThan, amountLessThan);
        }else{
            allAccountsFromClient = this.accountService.getAccountsByClientId(clientId);
        }

        if (allAccountsFromClient.isEmpty()){
            ctx.result("No accounts found");
            ctx.status(404);
            logger.error("no accounts found");
        }
        else {
            Gson gson = new Gson();
            String accountsJSON = gson.toJson(allAccountsFromClient);
            ctx.result(accountsJSON);
            ctx.status(200);
        }

    };


}













/********************
 public Handler getAccountByBalanceHandler = (ctx) -> {
 int id = Integer.parseInt(ctx.pathParam("id"));
 String amountLessThan = ctx.queryParam("amountLessThan", "-1");
 String amountGreaterThan = ctx.queryParam("amountGreaterThan", "-1");

 Set<Account> filteredAccount;

 if (amountLessThan != "-1" && amountGreaterThan != "-1") {
 filteredAccount = this.accountService.getAccountsByClientId(id);

 //       capturedClient.addAccount(clientAccounts);
 //        this.clientService.updateClient(capturedClient);
 Gson gson = new Gson();
 String rangeJSON = gson.toJson(filteredAccount);
 ctx.result(rangeJSON);
 ctx.status(200);

 } else {
 float minAmount = Float.parseFloat(amountGreaterThan);
 float maxAmount = Float.parseFloat(amountLessThan);

 filteredAccount = this.accountService.getAccountsByClientId(id, minAmount, maxAmount);
 }

 if (filteredAccount.isEmpty() ) {
 ctx.result("Such client with id of " + id + " does not exist");
 System.out.println("Such client with id of " + id + " does not exist");
 ctx.status(404);
 }
 else {
 Gson gson = new Gson();
 String accountsJSON = gson.toJson(filteredAccount);
 ctx.result(accountsJSON);
 ctx.status(200);
 }
 //                Gson gson = new Gson();
 //                String rangeJSON = gson.toJson(filteredAccount);
 //                ctx.result(rangeJSON);
 //                ctx.status(200);
 //            }





 };

 *****************************************************************************/






/*****************************************************************************
 * https://www.tutorialspoint.com/gson/gson_quick_guide.htm
 * Step 3 − Serialize Object to JSON
 * Use toJson() method to get the JSON string representation of an object.
 *
 * //Object to JSON Conversion
 * jsonString = gson.toJson(student);
 *
 *****************************************************************************/