package dev.lebassi.entities;

public class Account  {

    private String accountType;
    private int accountId;
    private double accountBalance;
    private int clientId;

    public Account() {
    }

    public Account(String accountType, int accountId, double accountBalance, int clientId) {
        this.accountType = accountType;
        this.accountId = accountId;
        this.accountBalance = accountBalance;
        this.clientId = clientId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountType='" + accountType + '\'' +
                ", accountId=" + accountId +
                ", accountBalance=" + accountBalance +
                ", clientId=" + clientId +
                '}';
    }

}
