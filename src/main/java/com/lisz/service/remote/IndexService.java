package com.lisz.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
Service接口，代圣贤（服务提供方的Controller）立言！指定了url就会访问该url地址，否则会把name参数当作服务名到注册中心中查询该名字服务，此时指定了url后可以随意命名
Feign原理：我们首先会添加@EnableFeignClients注解开启对 FeignClient扫描加载处理，扫描后会注入到SpringIOC容器，当定义的feign接口方法被调用时，通过JDK代理的方式，
生成具体的RequestTemplate，RequestTemplate生成Request，交给URLConnection处理，并结合LoadBalanceClient与Ribbon，以负载均衡的方式发起服务间的调用
原文链接：https://blog.csdn.net/zhuyu19911016520/java/article/details/84933268
 */
@FeignClient(name = "search-github", url="https://api.github.com")
public interface IndexService {
    @GetMapping("/search/repositories")
    String search(@RequestParam("q") String query);
}