package com.lisz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PassengerAPIApplication {
    public static void main( String[] args ) {
        SpringApplication.run(PassengerAPIApplication.class, args);
    }
}
