package com.lisz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 去到http://localhost:9001/eureka/apps/api-passenger查看Eureka Client的元数据信息，
 去到http://localhost:9001/eureka/apps/service-sms 可以查看service-sms服务的
 元数据信息，只换一下服务名就行，其实背后都是去访问了Eureka注册中心
 自我保护机制是Eureka Server和A服务的通信是好的，但是跟B服务之间的网络有问题，这时候自我
 保护，及时收不到B发过来的心跳也先不剔除B，以期望等什么时候网络好了。这样就避免了A同步到
 Eureka Server的数据之后，即使AB之间没问题，A也找不到B。因为A会定时向Eureka Server
 拉取服务信息，就包括B的。当Server在短时间内丢失过多的客户端时，会进入自我保护模式。
 宁可保留健康的、不健康的，也不盲目注销任何健康的服务。
 开启自我保护时，有心跳的instance数大于85%(默认)，才剔除，意思就是85%的机器都可以ping
 通，而你却不行，大概率是你自己的问题，剔除你；关闭自我保护的时候则一旦收不到心跳，就会
 剔除
*/

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
