package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.AccountNumberLimitException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Client implements Serializable {
    private long id;

    private String name;

    private List<AbstractAccount> accounts = new ArrayList<>(2);

    private AbstractAccount activeAccount;

    private Gender gender;

    private String city;

    public Client() {
    }

    public Client(String name) {
        this(name, Gender.UNDEFINED);
    }

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public double getBalance() {

        return 0.0;
    }

    public void deposit(double amount) {

    }

    public void withdraw(double amount) {
    }

    public void removeAccount(Class type) {
        accounts = accounts.stream()
                .filter(a -> a.getClass() != type)
                .collect(Collectors.toList());
    }

    public void setAccounts(List<AbstractAccount> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
    }

    public List<AbstractAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public AbstractAccount getAccount(Class type) {
        for (AbstractAccount account : accounts) {
            if (account.getClass().equals(type)) {
                return account;
            }
        }
        return null;
    }

    public void addAccount(AbstractAccount account) {
        if (accounts.size() >= 2) {
            throw new AccountNumberLimitException();
        }

        if (account != null) {
            accounts.add(account);

            if (activeAccount == null)
            {
                activeAccount = account;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private StringBuilder getSimpleInfoBuilder() {
        StringBuilder builder = new StringBuilder();

        builder.append("\nClient: ")
                .append(name)
                .append("\nGender: ")
                .append(getGender());

        return builder;
    }

    @Override
    public String toString() {
        StringBuilder builder = getSimpleInfoBuilder();

        builder.append("\nAccounts:");

        for (AbstractAccount account : accounts) {
            builder.append(account.toString());
        }

        builder.append("\nActive account: ");

        builder.append(activeAccount != null ? activeAccount.getClass() : "not set");

        return builder.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractAccount getActiveAccount() {

        if (activeAccount == null && accounts != null && !accounts.isEmpty()) {
            AbstractAccount account = getAccount(CheckingAccount.class);

            if (account == null) {
                account = accounts.iterator().next();
            }

            activeAccount = account;
        }

        return activeAccount;
    }

    public void setActiveAccount(AbstractAccount activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public enum Gender {
        MALE("Mr"), FEMALE("Ms"), UNDEFINED("");

        private String prefix;

        String getSalutation() {
            return prefix;
        }

        Gender(String prefix) {
            this.prefix = prefix;
        }

        public static Gender parse(String s) {
            if ("m".equals(s)) {
                return MALE;
            } else if ("f".equals(s)) {
                return FEMALE;
            }
            return UNDEFINED;
        }
    }
}
