package dev.lebassi.daos;

import dev.lebassi.entities.Client;
import dev.lebassi.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientDaoPostgressDb implements ClientDAO {

    Logger logger = Logger.getLogger(ClientDaoPostgressDb.class.getName());


    @Override
    public Client createClient(Client client) {

        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = " insert into client (firstName, lastName) values (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getFirstName());
            ps.setString(2,client.getLastName());
   //       ps.setInt(3, client.getClientId());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int key = rs.getInt("clientid");
            client.setClientId(key);
            return client;


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    @Override
    public Set<Client> getAllClients() {
        Set<Client> allClients = new HashSet<Client>();
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            rs.next(); // gets first actual client/moves cursor

            while(rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt("clientId"));
                client.setFirstName(rs.getString("firstName"));
                client.setLastName(rs.getString("lastName"));


                allClients.add(client);
            }
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to get all clients", sqlException);
        }
        System.out.println(allClients);
        return allClients;
    }

    @Override
    public Client getClientById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from client where clientid = ?";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next(); // gets first actual client/moves cursor
            Client client = new Client();
            client.setClientId(rs.getInt("clientid"));
            client.setFirstName(rs.getString("firstName"));
            client.setLastName(rs.getString("lastName"));
            return client;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to get client", sqlException);
            return null;
        }
    }

    @Override
    public Client updateClient(Client client) {
        try(Connection conn = ConnectionUtil.createConnection()){
            // sql using nice prepared statements
            // write a string and update the string directly with your values
            String sql = "update client set firstName= ?, lastName = ?  where clientid = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,  client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setInt(3, client.getClientId());
            ps.execute();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to update client", sqlException);
        }
        return client;
    }

    @Override
    public boolean deleteClientById(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from client where clientid = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
     //       ps.execute();
     //       return true;
            int deleted = ps.executeUpdate();
            if (deleted == 1){
                return true;
            }
            return false;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            logger.error("unable to delete client", sqlException);
            return false;
        }
    }
}
