package dev.lebassi.daos;


import dev.lebassi.entities.Account;

import java.util.Set;

// Every entity will have it's own DAO
// DAO Data Access Object is a class designed to perform CRUD operations for a single entity
// CRUD Create, Read, Update, Delete
public interface AccountDAO {

    //CREATE
    Account createAccount(Account account);

    //READ
                //   Set<Account> getAllAccounts();        *****
                // Account getAccountById(int id);         *****
    Set<Account> getAccountsByClientId(int id);
    Set<Account> getAccountsByClientId(int id, float balanceMin, float balanceMax);
 //   Set<Account> getAccountsByClientIdGivenMinMaxQueries(int id, float balanceMin, float balanceMax);
    Account getAccountByIds(int clientId, int accountId);


    //UPDATE
    Account updateAccount(Account account);

    //DELETE
                // boolean deleteAccountById(int id);    *****
    boolean deleteAccountByIds(int clientId, int accountId);
}
