package dev.lebassi.daotest;

import dev.lebassi.daos.AccountDAO;
import dev.lebassi.daos.AccountDaoPostgressDb;
import dev.lebassi.entities.Account;
import dev.lebassi.entities.Client;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTest {

    private static AccountDAO adao = new AccountDaoPostgressDb();
    private static Account testAccount = null;

    @Test
    @Order(1)
    void create_account() {


        Account timWerner = new Account("Savings Account", 1, 38245.46, 1);   // String accountType, int accountId, double accountBalance, int clientId
        System.out.println("Initial timWerner object is " + timWerner); // Initially, the id should BE zero
        System.out.println("Initial timWerner account id is " + timWerner.getAccountId());
        adao.createAccount(timWerner);
        System.out.println("Updated timWerner object is " + timWerner); // Initially, the id should BE zero
        System.out.println("Updated timWerner account id is " + timWerner.getAccountId());
        testAccount = timWerner;
        Assertions.assertNotEquals(0, timWerner.getClientId());

    }


    /*******************************
    @Test
    @Order(2)
    void get_all_accounts(){      // getAllAccounts

        Account a1 = new Account("Savings", 1, 38245.46,  1);
        Account a2 = new Account("Checking", 2, 12384.12, 1);
        Account a3 = new Account("CD", 3, 85400.00, 1);

        adao.createAccount(a1);
        adao.createAccount(a2);
        adao.createAccount(a3);

        Set<Account> allAccounts =  adao.getAllAccounts();                  //
 //     Assertions.assertTrue(allAccounts.size()>2);
    }
     *******************************************/


    @Test
    @Order(2)
    void get_accounts_by_clientId() {
        int id = testAccount.getAccountId();
        Account a1 = new Account("Savings", 1, 38245.46,  1);
        Account a2 = new Account("Checking", 2, 12384.12, 1);
        Account a3 = new Account("CD", 3, 85400.00, 1);
        Account a4 = new Account("401K", 4, 85400.00, 1);
        adao.createAccount(a1);
        adao.createAccount(a2);
        adao.createAccount(a3);
        adao.createAccount(a4);
        Set<Account> allAccounts = adao.getAccountsByClientId(id);

        System.out.println("All accounts size is: " + allAccounts.size());

        for(Account act : allAccounts) {
            System.out.println(act);
        }

        Assertions.assertTrue(allAccounts.size()>2);
    }


    @Test
    @Order(3)
    void get_accounts_by_client_ids_given_minmax_queries_test() {
        int id = 10;
        Set<Account> allAccounts = adao.getAccountsByClientId(id, 400, 2000000 );
        Assertions.assertTrue(allAccounts.size() >= 0);
        System.out.println(allAccounts);
    }


    @Test
    @Order(4)
    void update_account(){
        Account account = adao.getAccountByIds(testAccount.getClientId(), testAccount.getAccountId()) ;       // grabbed the account from the database
        System.out.println("Test Account: " + testAccount);
        System.out.println(account);
        account.setAccountBalance(38245.46);                   // account.setAccountType("Savings");
        System.out.println("Initial balance is " + account);
        adao.updateAccount(account);
        System.out.println("Updated account is " + account);

        Account updatedAccount = adao.getAccountByIds(testAccount.getClientId(), testAccount.getAccountId());
        Assertions.assertEquals(38245.46, updatedAccount.getAccountBalance());
        System.out.println(updatedAccount);

    }


    @Test
    @Order(5)
    void delete_account_by_id() {
        int id = testAccount.getAccountId();
        boolean result = adao.deleteAccountByIds(testAccount.getClientId(), testAccount.getClientId());
        Assertions.assertTrue(result);
    }


}
