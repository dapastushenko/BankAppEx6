package com.luxoft.bankapp;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Profile("dev")
@PropertySource("classpath:clients.properties")
public class DevConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = "client1")
    public Client getDemoClient1() {
        String name = environment.getProperty("client1");

        Client client = new Client(name, Client.Gender.MALE);
        client.setCity("Moscow");

        AbstractAccount savingAccount =
                (SavingAccount) applicationContext.getBean("savingAccount1");
        client.addAccount(savingAccount);

        AbstractAccount checkingAccount =
                (CheckingAccount) applicationContext.getBean("checkingAccount1");
        client.addAccount(checkingAccount);

        return client;
    }

    @Bean(name = "savingAccount1")
    public SavingAccount getDemoSavingAccount1() {
        return new SavingAccount(1000);
    }

    @Bean(name = "checkingAccount1")
    public CheckingAccount getDemoCheckingAccount1() {

        CheckingAccount checkingAccount = new CheckingAccount(1000);
        checkingAccount.deposit(4000);

        return checkingAccount;
    }

    @Bean(name = "checkingAccount2")
    public CheckingAccount getDemoCheckingAccount2()
    {
        CheckingAccount checkingAccount = new CheckingAccount(1500);
        checkingAccount.withdraw(500);

        return checkingAccount;
    }

    @Bean(name = "client2")
    public Client getDemoClient2()
    {
        String name = environment.getProperty("client2");

        Client client = new Client(name, Client.Gender.MALE);
        client.setCity("Kiev");

        AbstractAccount checking =
                (CheckingAccount) applicationContext.getBean("checkingAccount2");
        client.addAccount(checking);

        return client;
    }
}
