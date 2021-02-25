package dev.lebassi.daos;

import dev.lebassi.entities.Account;
import dev.lebassi.entities.Client;
import dev.lebassi.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoPostgressDb implements AccountDAO {

    Logger logger = Logger.getLogger(ClientDaoPostgressDb.class.getName());


    @Override
    public Account createAccount(Account account) {

        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = " insert into account (accountType, accountId, accountBalance, clientId) values (?,?,?,?)";  // accountType, int accountId, double accountBalance, int clientId
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getAccountType());       // client.getFirstName()
            ps.setInt(2, account.getAccountId());
            ps.setDouble(3, account.getAccountBalance());
            ps.setInt(4, account.getClientId());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            //          int key = rs.getInt("clientid");
            //          account.setAccountId(key);
            return account;


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Account> getAccountsByClientId(int id) {
        Set<Account> allAccounts = new HashSet<>();
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from account where clientId = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) { // gets first actual client/moves cursor
                //     Client client = new Client();
                Account account = new Account();
                account.setClientId(rs.getInt("clientId"));
                account.setAccountId(rs.getInt("accountId"));
                account.setAccountType(rs.getString("accountType"));
                account.setAccountBalance(rs.getDouble("accountBalance"));
                allAccounts.add(account);
            }

      //           return allAccounts;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get Accounts By ClientId", sqlException);
            return allAccounts;

        }
          return allAccounts;
    }
/********
    @Override
    public Set<Account> getAccountsByClientId(int id, float balanceMin, float balanceMax) {
        Set<Account> allAccounts = new HashSet<>();
        try (Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from account where clientId = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) { // gets first actual client/moves cursor
                //     Client client = new Client();
                Account account = new Account();
                account.setClientId(rs.getInt("clientId"));
                account.setAccountId(rs.getInt("accountId"));
                account.setAccountType(rs.getString("accountType"));
                account.setAccountBalance(rs.getDouble("accountBalance"));
                allAccounts.add(account);
            }

            //           return allAccounts;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("unable to get Accounts By ClientId", sqlException);
            return allAccounts;

        }
        return getAccountByBalance(allAccounts, balanceMin,balanceMax);
    }
**********************/
    @Override
    public Account getAccountByIds(int clientId, int accountId) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where clientId=? and accountId = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientId);     //    (1, account.getClientId())
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();
            rs.next(); // gets first actual account/moves cursor
            //     Client client = new Client();
            Account account = new Account();
            account.setClientId(rs.getInt("clientId"));
            account.setAccountId(rs.getInt("accountId"));
            account.setAccountType(rs.getString("accountType"));
            account.setAccountBalance(rs.getDouble("accountBalance"));
            return account;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to get account", sqlException);
            return null;
        }
    }


    /******************
    @Override
    public Set<Account> getAccountsByClientIdGivenMinMaxQueries(int id, float balanceMin, float balanceMax) {
        return null;
    }

    @Override
    public Account getAccountByIds(int clientId, int accountId) {
        return null;
    }
    ***************************/

//    @Override
//    public Set<Account> getAllAccounts() {
//        return null;
//    }


    /***************************
    @Override
    public Account getAccountById(int id) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where accountid = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next(); // gets first actual account/moves cursor
     //     Client client = new Client();
            Account account = new Account();
            account.setClientId(rs.getInt("clientid"));
            account.setAccountId(rs.getInt("accountid"));
            account.setAccountType(rs.getString("accounttype"));
            account.setAccountBalance(rs.getDouble("accountbalance"));
            return account;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to get account", sqlException);
            return null;
        }
    }

     **************************/

    @Override
    public Account updateAccount(Account account) {
        try(Connection conn = ConnectionUtil.createConnection()){
            // sql using nice prepared statements
            // write a string and update the string directly with your values
            // accountType, int accountId, double accountBalance, int clientId
            String sql = "update account set accountType= ?, accountBalance= ?  where clientId = ? and accountId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,  account.getAccountType());
            ps.setInt(2,  account.getAccountId());
            ps.setDouble(3, account.getAccountBalance());
            ps.setInt(4,  account.getClientId());
            ps.execute();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to update client account", sqlException);
        }
        return account;
    }

    @Override
    public boolean deleteAccountByIds(int clientId, int accountId) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where clientId =? and accountId = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
      //      ps.setInt(clientId, accountId);
            ps.setInt(1,clientId);
            ps.setInt(2, accountId);
    //        ps.setInt(2,clientId);
            //       ps.execute();
            //       return true;
            int deleted = ps.executeUpdate();
            if (deleted > 0){
                return true;
            }
            return false;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to delete account", sqlException);
            return false;
        }
    }

    /************************************
    @Override
    public boolean deleteAccountById(int id) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where accountId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            //       ps.execute();
            //       return true;
            int deleted = ps.executeUpdate();
            if (deleted > 0){
                return true;
            }
            return false;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to delete account", sqlException);
            return false;
        }

    }
    ***************************/

    private AccountDAO accountDAO;


    @Override
    public Set<Account> getAccountsByClientId(int id, float balanceMin, float balanceMax) {
        Set<Account> allAccounts = new HashSet<Account>();
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where clientId = ? and accountbalance > ? and accountbalance < ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ps.setFloat(2, balanceMin);
            ps.setFloat(3, balanceMax);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account();
                account.setClientId(rs.getInt("clientId"));
                account.setAccountId(rs.getInt("accountId"));
                account.setAccountType(rs.getString("accountType"));
                account.setAccountBalance(rs.getFloat("accountbalance"));

                allAccounts.add(account);
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to get all accounts of client "+id);
        }
        return allAccounts;
    }


    public Set<Account> getAccountByBalance(Set<Account> allAccounts, double minAmount, double maxAmount) {
        Set<Account> filteredAccounts= new HashSet<>();
        for(Account a: allAccounts)
        {
            if(a.getAccountBalance() >=minAmount && a.getAccountBalance()<=maxAmount)
            {
                filteredAccounts.add(a);
            }
            System.out.println("Filtered range " + a);
        }
        return filteredAccounts;
    }


}
