package com.luxoft.bankapp;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {

        SpringApplication.run(BankApplication.class, args);
    }

    @Bean
    public static ApplicationContextProvider contextProvider(){
        return new ApplicationContextProvider();
    }
    public static class  ApplicationContextProvider implements ApplicationContextAware{
        private static ApplicationContext context;

        public ApplicationContext getApplicationContext() {
            return context;
        }

        @Override
        public void setApplicationContext(ApplicationContext context) throws BeansException {
            this.context = context;
        }
    }
}
