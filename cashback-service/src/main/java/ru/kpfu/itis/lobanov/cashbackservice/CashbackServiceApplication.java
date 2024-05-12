package ru.kpfu.itis.lobanov.cashbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CashbackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashbackServiceApplication.class, args);
    }

}
