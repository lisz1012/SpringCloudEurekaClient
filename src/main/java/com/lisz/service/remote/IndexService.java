package com.lisz.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Service接口，代圣贤（服务提供方的Controller）立言！指定了url就会访问该url地址，否则会把name参数当作服务名到注册中心中查询该名字服务，此时指定了url后可以随意命名
@FeignClient(name = "search-github", url="https://api.github.com")
public interface IndexService {
    @GetMapping("/search/repositories")
    String search(@RequestParam("q") String query);
}