package com.lisz.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
Service接口，代圣贤（服务提供方的Controller）立言！指定了url就会访问该url地址，否则会把name参数当作服务名到注册中心中查询该名字服务，此时指定了url后可以随意命名
Feign原理：我们首先会添加@EnableFeignClients注解开启对 FeignClient扫描加载处理，扫描后会注入到SpringIOC容器，当定义的feign接口方法被调用时，通过JDK代理的方式，
生成具体的RequestTemplate，RequestTemplate生成Request，交给URLConnection处理，并结合LoadBalanceClient与Ribbon，以负载均衡的方式发起服务间的调用
原文链接：https://blog.csdn.net/zhuyu19911016520/java/article/details/84933268

在使用@FeignClient注解的时候 是默认使用了ribbon进行客户端的负载均衡的,默认的是随机的策略,那么如果我们想要更改策略的话,需要修改消费者yml中的配置,如下:
stu-provide:  # 与server和eureka等是平级的
      ribbon:
    #    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #配置规则 随机
    #    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule #配置规则 轮询
    #    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RetryRule #配置规则 重试
    #    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.WeightedResponseTimeRule #配置规则 响应时间权重
        NFLoadBalancerRuleClassName: com.netflix.loadbalancer.BestAvailableRule #配置规则 最空闲连接策略
        ConnectTimeout: 500 #请求连接超时时间
        ReadTimeout: 1000 #请求处理的超时时间
        OkToRetryOnAllOperations: true #对所有请求都进行重试
        MaxAutoRetriesNextServer: 2 #切换实例的重试次数
        MaxAutoRetries: 1 #对当前实例的重试次数
 */
// 访问的第三方API完整路径是：https://api.github.com/search/repositories?q=spring-cloud 其中spring-cloud要等待参数的传入
@FeignClient(name = "search-github", url="https://api.github.com")
public interface IndexService {
    @GetMapping("/search/repositories")
    String search(@RequestParam("q") String query);
}