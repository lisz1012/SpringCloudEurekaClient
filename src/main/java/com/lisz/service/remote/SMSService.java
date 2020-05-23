package com.lisz.service.remote;

import com.lisz.model.SMSMetaData;
import com.lisz.model.SMSMetaDataRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 Service接口，代圣贤（服务提供方的Controller）立言！这里没有指定url，会访问Eureka上name是"sms-service"的服务，该服务必须已注册
 Feign原理：我们首先会添加@EnableFeignClients注解开启对 FeignClient扫描加载处理，扫描后会注入到SpringIOC容器，当定义的feign接口方法被调用时，通过JDK代理的方式，
 生成具体的RequestTemplate，RequestTemplate生成Request，交给URLConnection处理，并结合LoadBalanceClient与Ribbon，以负载均衡的方式发起服务间的调用
 原文链接：https://blog.csdn.net/zhuyu19911016520/java/article/details/84933268
 */
@FeignClient(name = "sms-service")
public interface SMSService {
    @PostMapping("/sms/metadata") // 跟Controller中的restTemplate.postForEntity调用的是同一个SMSService的Rest API，这里用了Feign，更加OOD
    SMSMetaData getSMSMetaData(@RequestBody SMSMetaDataRequest request);
}
