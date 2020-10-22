package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {
    private double overdraft = 0;

    public CheckingAccount() {

    }

    public CheckingAccount(double overdraft) {
        setOverdraft(overdraft);
    }

    public void setOverdraft(double amount) {
        if (overdraft < 0) {
            return;
        }

        overdraft = amount;
    }

    public double getOverdraft() {
        return overdraft;
    }

    @Override
    public void withdraw(double amount) throws OverdraftLimitExceededException {
        if (getBalance() + overdraft < amount) {
            throw new OverdraftLimitExceededException(
                    String.valueOf(getId()), getBalance() + overdraft);
        }

        setBalance(getBalance() - amount);
    }
}
