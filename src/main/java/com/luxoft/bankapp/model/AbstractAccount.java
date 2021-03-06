package com.luxoft.bankapp.model;



import javax.persistence.*;
import java.io.Serializable;



import java.io.Serializable;

@Entity
@Table(name = "ACCOUNT")
public abstract class AbstractAccount implements Serializable {

    private long id;

    private double balance;

    public void deposit(double amount) {
        if (amount < 0) {
            return;
        }

        balance += amount;
    }

    public abstract void withdraw(double amount);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractAccount that = (AbstractAccount) o;

        return Double.compare(that.balance, balance) == 0;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("\n")
                .append("\n\tbalance = ")
                .append(balance);
        return builder.toString();
    }

    @Column(name = "BALANCE")
    public double getBalance() {
        return balance;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
