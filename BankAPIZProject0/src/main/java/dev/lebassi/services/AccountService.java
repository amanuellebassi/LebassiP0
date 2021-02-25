package dev.lebassi.services;

import dev.lebassi.entities.Account;

import java.util.Set;

public interface AccountService {
    Account registerAccount(Account account);

    // Set<Account> getAllAccounts();
    Set<Account> getAccountsByClientId(int id);
    Set<Account> getAccountsByClientId(int id, float balanceMin, float balanceMax);
    Account getAccountByIds(int clientId, int accountId);
   // Set<Account> getAccountsByTitle(String title);

    //Account getAccountById(int id);


    Account updateAccount(Account account);

   // boolean deleteAccountById(int id);

    boolean deleteAccountByIds(int clientId, int accountId);





}
