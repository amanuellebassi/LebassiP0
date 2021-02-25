package dev.lebassi.entities;

public class Client {
    private String firstName;
    private String lastName;
    private int clientId;

    public Client() {
    }

    public Client(String firstName, String lastName, int clientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }


}
