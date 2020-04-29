package com.lisz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.IOException;

@EnableEurekaClient
@SpringBootApplication
public class PassengerAPIApplication {
    public static void main( String[] args ) {
        SpringApplication.run(PassengerAPIApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
