package com.luxoft.bankapp;

import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.BankReportServiceImpl;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.demo.BankInitializationService;
import com.luxoft.bankapp.service.demo.DemoBankInitializationService;
import com.luxoft.bankapp.service.storage.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTests1 {
    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    private Banking banking;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankReportService bankReport;

    @Autowired
    private BankInitializationService initializationService;

    @Test
    public void clientRepositoryBeanConfiguration() {
        assertNotNull(clientRepository, "clientRepository bean should be configured");
        assertTrue((clientRepository instanceof ClientRepository), "clientRepository should be instantiated with ClientRepository class");
    }

    @Test
    public void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    public void bankingBeanAnnotation() {
        Annotation annotation = null;

        try {
            annotation = BankingImpl.class.getDeclaredField("repository")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("BankingImpl should contains repository field");
        }

        assertNotNull(annotation, "repository field should contain annotation @Autowired");
    }

    @Test
    public void bankReportConfiguration() {
        assertNotNull(bankReport, "bankReport bean should be configured");
        assertTrue((bankReport instanceof BankReportServiceImpl), "bankReport should be instantiated with BankReportServiceImpl class");
    }

    @Test
    public void bankReportBeanAnnotation() {
        Annotation annotation = null;

        try {
            annotation = BankReportServiceImpl.class.getDeclaredField("repository")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("BankingImpl should contains repository field");
        }

        assertNotNull(annotation, "repository field should contain annotation @Autowired");
    }

    @Test
    public void initializationServiceConfiguration() {
        assertNotNull(initializationService, "initializationService bean should be configured");
        assertTrue((initializationService instanceof DemoBankInitializationService),
                "initializationService should be instantiated with DemoBankInitializationService class");
    }

    @Test
    public void initializationServiceBeanAnnotation1() {
        String value = null;

        try {
            Annotation annotation = DemoBankInitializationService.class.getAnnotation(Profile.class);
            value = ((Profile) annotation).value()[0];
        } catch (RuntimeException e) {
            fail("DemoBankInitializationService should contain Profile annotation");
        }

        assertEquals(value, "dev", "Profile annotation should contain value: dev");
    }


    @Test
    public void initializationClient1() {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);

        assertEquals(2, client.getAccounts().size());
    }

    @Test
    public void client1SavingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        AbstractAccount account = client.getAccount(SavingAccount.class);

        assertNotNull(account,
                client.getName() + "should have saving account");

        assertEquals(1000, account.getBalance());
    }

    @Test
    public void client1CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have checking account");

        assertEquals(4000, account.getBalance());
        assertEquals(1000, account.getOverdraft());
    }

    @Test
    public void initializationClient2() {
        Client client = banking.getClient(CLIENT_NAMES[1]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[1]);

        assertEquals(1, client.getAccounts().size());
    }

    @Test
    public void client2CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[1]);

        CheckingAccount account = (CheckingAccount) client.getAccount(CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have checking account");

        assertEquals(-500, account.getBalance());
        assertEquals(1500, account.getOverdraft());
    }
}
