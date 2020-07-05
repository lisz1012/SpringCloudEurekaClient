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

 在使用@FeignClient注解的时候 是默认使用了ribbon进行客户端的负载均衡的,默认的是随机的策略,那么如果我们想要更改策略的话,需要修改消费者yml中的配置,如下:


stu-provide:  # 服务提供方的application name 与server和eureka等是平级的
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
        MaxAutoRetries: 1 #对当前实例的重试次数。


随机:几个提供者间随机访问
轮询:轮流访问
重试:在一段时间内通过RoundRobinRule选择服务实例，一段时间内没有选择出服务则线程终止
响应时间权重:根据平均响应时间来计算权重
https://blog.csdn.net/yucaifu1989/article/details/105020317
原文链接：https://blog.csdn.net/guoqiusheng/article/details/88898426
 */
@FeignClient(name = "sms-service") //Eureka中注册的服务提供者的spring.application.name
public interface SMSService {
    @PostMapping("/sms/metadata") // 跟Controller中的restTemplate.postForEntity调用的是同一个SMSService的Rest API，这里用了Feign，更加OOD
    SMSMetaData getSMSMetaData(@RequestBody SMSMetaDataRequest request);
}
