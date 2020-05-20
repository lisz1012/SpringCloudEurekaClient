package com.lisz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service-instance")
public class ServiceInstanceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/hello")
    public void hello() {
        System.out.println("hello");
    }

    @GetMapping("/query-by-application-name/{applicationName}")
    public List<ServiceInstance> getInstance(@PathVariable String applicationName) {
        List<ServiceInstance> list = discoveryClient.getInstances(applicationName);
        System.out.println(list);
        return list;
    }
}
