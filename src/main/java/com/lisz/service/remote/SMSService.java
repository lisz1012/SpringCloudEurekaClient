package com.lisz.service.remote;

import com.lisz.model.SMSMetaData;
import com.lisz.model.SMSMetaDataRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Service接口，代圣贤（服务提供方的Controller）立言！这里没有指定url，会访问Eureka上name是"sms-service"的服务，该服务必须已注册
@FeignClient(name = "sms-service")
public interface SMSService {
    @PostMapping("/sms/metadata") // 跟Controller中的restTemplate.postForEntity调用的是同一个SMSService的Rest API，这里用了Feign，更加OOD
    SMSMetaData getSMSMetaData(@RequestBody SMSMetaDataRequest request);
}
