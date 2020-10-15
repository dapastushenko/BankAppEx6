package com.luxoft.bankapp;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:clients.properties")
public class BankApplication {

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);

    }

    @Bean
    CommandLineRunner init(ApplicationContext context)
    {
        return env ->
        {
            Banking banking = context.getBean(BankingImpl.class);

            Client client_1 = (Client) context.getBean("client1");
            Client client_2 = (Client) context.getBean("client2");

            banking.addClient(client_1);
            banking.addClient(client_2);
        };
    }

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
