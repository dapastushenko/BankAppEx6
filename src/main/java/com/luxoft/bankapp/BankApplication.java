package com.luxoft.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DevConfiguration.class)
public class BankApplication {

    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);
    }
}
