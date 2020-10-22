package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Client;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BankingImpl implements Banking {

    @Override
    public Client addClient(Client client) {
        return null;
    }

    @Override
    public Client getClient(String name) {
        throw new ClientNotFoundException(name);
    }

    @Override
    public List<Client> getClients() {
        return null;
    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public AbstractAccount createAccount(Client client, Class type) {
        return null;
    }

    @Override
    public AbstractAccount getAccount(Client client, Class type) {
        for (AbstractAccount account : client.getAccounts()) {
            if (type == account.getClass()) {
                return account;
            }
        }

        throw new AccountNotFoundException();
    }

    @Override
    public List<AbstractAccount> getAllAccounts() {
        return new ArrayList<>();
    }

    @Override
    public List<AbstractAccount> getAllAccounts(Client client) {
        return null;
    }

    @Override
    public void transferMoney(Client from, Client to, double amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }
}
