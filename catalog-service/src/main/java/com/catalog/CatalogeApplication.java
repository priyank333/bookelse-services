package com.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CatalogeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogeApplication.class, args);
    }
}
