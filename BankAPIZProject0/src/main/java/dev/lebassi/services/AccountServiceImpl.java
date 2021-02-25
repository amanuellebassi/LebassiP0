package dev.lebassi.services;

import dev.lebassi.daos.AccountDAO;
import dev.lebassi.daos.ClientDAO;
import dev.lebassi.entities.Account;

import java.util.HashSet;
import java.util.Set;

public class AccountServiceImpl implements AccountService {

    private AccountDAO adao;

    // Dependency injection
    // A service is created by passing in the dependencies it needs


    public AccountServiceImpl(AccountDAO adao) {
        this.adao = adao;
    }


    @Override
    public Account registerAccount(Account account) {
       return  this.adao.createAccount(account);

    }

    @Override
    public Set<Account> getAccountsByClientId(int id) {
        return this.adao.getAccountsByClientId(id);
    }

    @Override
    public Set<Account> getAccountsByClientId(int id, float balanceMin, float balanceMax) {
        return this.adao.getAccountsByClientId(id,balanceMin,balanceMax);
    }

    @Override
    public Account getAccountByIds(int clientId, int accountId) {
        return this.adao.getAccountByIds(clientId, accountId);
    }

    @Override
    public Account updateAccount(Account account) {
        return this.adao.updateAccount(account);
    }

    @Override
    public boolean deleteAccountByIds(int clientId, int accountId) {
        return this.adao.deleteAccountByIds(clientId, accountId);
    }
}
