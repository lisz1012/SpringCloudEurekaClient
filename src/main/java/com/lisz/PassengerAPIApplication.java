package com.lisz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class PassengerAPIApplication {

    @Bean
    @LoadBalanced // 这个注解原理是加了一个拦截器, 获取URL，通过LB设置选一个ServiceInstance，通过service name获取到IP和端口
    // ribbon-loadbalancer:2.3.0 的LoadBalancerContext.reconstructURIWithServer
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main( String[] args ) {
        SpringApplication.run(PassengerAPIApplication.class, args);
        /*try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
