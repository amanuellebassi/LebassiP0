package dev.lebassi.services;

import dev.lebassi.entities.Client;

import java.util.Set;

public interface ClientService {
    Client registerClient(Client client);

    Set<Client> getAllClients();

    Client getClientById(int id);

    boolean deleteClientById(int id);

    Client updateClient(Client client);


}
