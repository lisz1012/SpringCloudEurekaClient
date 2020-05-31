package com.lisz.controller;

import com.lisz.model.SMSMetaData;
import com.lisz.model.SMSMetaDataRequest;
import com.lisz.service.remote.IndexService;
import com.lisz.service.remote.SMSService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IndexService indexService;

    @Autowired
    private SMSService smsService;

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

    @PostMapping("/get-message-by-id")
    public Object getMessageById(@RequestParam int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        SMSMetaDataRequest request = new SMSMetaDataRequest();
        request.setId(id);
        if (id % 2 == 0) {
            request.setType("PAID");
        } else {
            request.setType("regular");
        }

        //HttpEntity<SMSMetaData> request = new HttpEntity<SMSMetaData>(data, headers);
        ResponseEntity<SMSMetaData> resultEntity = restTemplate.postForEntity("http://sms-service/sms/metadata", request, SMSMetaData.class);// 这里sms-service后面写的端口号将被忽略
        SMSMetaData smsMetaData = resultEntity.getBody();
        return smsMetaData;
    }

    // Call Github的open API 拿到String
    @GetMapping("/search-in-github/{query}")
    public String searchInGithub(@PathVariable String query) {
        return indexService.search(query);
    }

    @PostMapping("/get-metadata")
    public SMSMetaData getMetadata(@RequestParam int id, @RequestParam String type) {
        SMSMetaDataRequest request = new SMSMetaDataRequest();
        request.setId(id);
        request.setType(type);
        return smsService.getSMSMetaData(request);
    }

    @GetMapping("/choseServiceName")
    public void choseServiceName() {
        String serviceName = "sms-service";
        ServiceInstance si = loadBalancerClient.choose(serviceName);
        System.out.println(ToStringBuilder.reflectionToString(si, ToStringStyle.MULTI_LINE_STYLE));
    }
}
