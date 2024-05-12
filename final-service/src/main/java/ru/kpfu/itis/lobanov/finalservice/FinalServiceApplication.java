package ru.kpfu.itis.lobanov.finalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FinalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalServiceApplication.class, args);
    }

}
