//package dev.lebassi.servicetests;
//
//import dev.lebassi.daos.ClientDaoPostgressDb;
//import dev.lebassi.entities.Client;
//import dev.lebassi.services.ClientService;
//import dev.lebassi.services.ClientServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//
//public class ClientServiceTests {
//    private static ClientService cs = new ClientServiceImpl(new ClientDaoPostgressDb());
//
//    @Test
//    @Order(1)
//    void register_client_test(){
//        Client client = new Client("Solomon", "Kennedy", 1);
//        cs.registerClient(client);
//
//        Assertions.assertNotEquals(0, client.getId());
//        Assertions.assertEquals(0, client.getNumberOfAccounts());
//    }
//
//    @Test
//    @Order(2)
//    void update_client_test(){
//        Client client = new Client(0,"Thomas","Wan",1996,850);
//        client = cs.updateClient(client);
//        Assertions.assertEquals("Thomas",client.getFirstName());
//    }
//
//
//}
