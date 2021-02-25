package dev.lebassi.services;

import dev.lebassi.daos.AccountDAO;
import dev.lebassi.daos.ClientDAO;
import dev.lebassi.entities.Account;
import dev.lebassi.entities.Client;

import java.util.Set;

public class ClientServiceImpl implements ClientService {

    // Our service is going to need a dao to get and save clients
    // ClientDAO is a dependency
    // our service will use a DAO to save things
    private ClientDAO cdao;

    // Dependency injection
    // A service is created by passing in the dependencies it needs

    public ClientServiceImpl(ClientDAO clientDAO) {

        this.cdao = clientDAO;
    }

    @Override
    public Client registerClient(Client client) {
        this.cdao.createClient(client);
        return client;
    }

    @Override
    public Set<Client> getAllClients() {

        return this.cdao.getAllClients();
    }

    @Override
    public Client getClientById(int id) {
        return this.cdao.getClientById(id);
    }

    @Override
    public boolean deleteClientById(int id) {

        return cdao.deleteClientById(id);
    }

    @Override
    public Client updateClient(Client client) {
        this.cdao.updateClient(client);
        return client;
    }
}
