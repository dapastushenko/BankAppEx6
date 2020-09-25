import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.model.Client.Gender;
import com.luxoft.bankapp.service.BankingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.luxoft.bankapp")
@PropertySource("classpath:clients.properties")
public class BankApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(BankApplication.class);

        workWithExistingClients(context);

        bankingServiceDemo(context);

        bankReportsDemo(context);
    }

    @Bean(name = "client1")
    public Client getDemoClient1() {
        String name = environment.getProperty("client1");

        Client client = new Client(name, Gender.MALE);
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
        return new CheckingAccount(1000);
    }

    @Bean(name = "checkingAccount2")
    public CheckingAccount getDemoCheckingAccount2()
    {
        return new CheckingAccount(1500);
    }

    @Bean(name = "client2")
    public Client getDemoClient2()
    {
        String name = environment.getProperty("client2");

        Client client = new Client(name, Gender.MALE);
        client.setCity("Kiev");

        AbstractAccount checking =
                (CheckingAccount) applicationContext.getBean("checkingAccount2");
        client.addAccount(checking);

        return client;
    }

    /*
     * Method that creates a few clients and initializes them with sample values
     */
    public static Banking initialize(ApplicationContext context) {

        Banking banking = context.getBean(BankingImpl.class);

        Client client_1 = (Client) context.getBean("client1");
        Client client_2 = (Client) context.getBean("client2");

        banking.addClient(client_1);
        banking.addClient(client_2);


        return banking;
    }

    public static void bankReportsDemo(ApplicationContext context) {

        System.out.println("\n=== Using BankReportService ===\n");

        BankReportService reportService = context.getBean(BankReportService.class);

        System.out.println("Number of clients: " + reportService.getNumberOfBankClients());

        System.out.println("Number of accounts: " + reportService.getAccountsNumber());

        System.out.println("Bank Credit Sum: " + reportService.getBankCreditSum());
    }

    public static void bankingServiceDemo(ApplicationContext context) {

        System.out.println("\n=== Initialization using Banking implementation ===\n");

        Banking banking = context.getBean(Banking.class);

        Client anna = new Client(CLIENT_NAMES[2], Gender.FEMALE);
        anna = banking.addClient(anna);

        AbstractAccount saving = banking.createAccount(anna, SavingAccount.class);
        saving.deposit(1000);

        banking.updateAccount(anna, saving);

        AbstractAccount checking = banking.createAccount(anna, CheckingAccount.class);
        checking.deposit(3000);

        banking.updateAccount(anna, checking);

        banking.getAllAccounts(anna).stream().forEach(System.out::println);
    }

    public static void workWithExistingClients(ApplicationContext context) {

        System.out.println("\n=======================================");
        System.out.println("\n===== Work with existing clients ======");

        Banking banking = context.getBean(Banking.class);

        Client jonny = banking.getClient(CLIENT_NAMES[0]);

        try {

            jonny.deposit(5_000);

        } catch (ActiveAccountNotSet e) {

            System.out.println(e.getMessage());

            jonny.setDefaultActiveAccountIfNotSet();
            jonny.deposit(5_000);
        }

        System.out.println(jonny);

        Client adam = banking.getClient(CLIENT_NAMES[1]);
        adam.setDefaultActiveAccountIfNotSet();

        adam.withdraw(1500);

        double balance = adam.getBalance();
        System.out.println("\n" + adam.getName() + ", current balance: " + balance);

        banking.transferMoney(jonny, adam, 1000);

        System.out.println("\n=======================================");
        banking.getClients().forEach(System.out::println);
    }
}
