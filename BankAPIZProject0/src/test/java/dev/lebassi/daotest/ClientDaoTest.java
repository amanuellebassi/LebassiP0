package dev.lebassi.daotest;

import dev.lebassi.daos.ClientDAO;
import dev.lebassi.daos.ClientDaoPostgressDb;
import dev.lebassi.entities.Client;
import org.junit.jupiter.api.*;

import java.util.Set;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTest {

    private static ClientDAO cdao = new ClientDaoPostgressDb();
    private static Client  testClient = null;


    @Test
    @Order(1)
    void create_client() {


        Client timWerner = new Client("Timothy", "Werner", 10001);
        System.out.println("Initial timWerner object is " + timWerner); // Initially, the id should BE zero
        System.out.println(" Initial timWerner id is " + timWerner.getClientId());
        cdao.createClient(timWerner);
        System.out.println("Updated timWerner object is " + timWerner); // Initially, the id should BE zero
        System.out.println("Updated timWerner id is " + timWerner.getClientId());
        testClient = timWerner;
        Assertions.assertNotEquals(0, timWerner.getClientId());

    }


    @Test
    @Order(2)
    void get_all_clients(){

       Client c1 = new Client("Joe", "Smith", 10017);
       Client c2 = new Client("Peter", "James", 10023);
       Client c3 = new Client("Mike", "Jordan", 10034);

       cdao.createClient(c1);
       cdao.createClient(c2);
       cdao.createClient(c3);

       Set<Client> allClients = cdao.getAllClients();
       Assertions.assertTrue(allClients.size()>2);
    }

    @Test
    @Order(3)
    void get_client_by_id() {
        int id = testClient.getClientId();

        Client client = cdao.getClientById(id);
        Assertions.assertEquals(testClient.getClientId(),client.getClientId());
        System.out.println("The client we retrieved is " + client);
    }

    @Test
    @Order(4)
    void update_client() {
        Client client = cdao.getClientById(testClient.getClientId());
    //    client.setClientId(9);
        client.setLastName("John Doe");
        cdao.updateClient(client);

        Client updatedClient = cdao.getClientById(testClient.getClientId());
        Assertions.assertEquals("John Doe", updatedClient.getLastName());
    }

    @Test
    @Order(5)
    void delete_client_by_id() {
        int id = testClient.getClientId();
        boolean result = cdao.deleteClientById(id);      // delete that client
        Assertions.assertTrue(result);
    }
}
