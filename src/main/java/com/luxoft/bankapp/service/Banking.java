package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Client;
import java.util.List;

public interface Banking {

    Client addClient(Client client);

    Client getClient(String name);

    List<Client> getClients();

    void deleteClient(Client client);

    AbstractAccount createAccount(Client client, Class type);

    AbstractAccount getAccount(Client client, Class type);

    List<AbstractAccount> getAllAccounts();

    List<AbstractAccount> getAllAccounts(Client client);

    void transferMoney(Client from, Client to, double amount);
}
