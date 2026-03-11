package com.ordenesservice.ordenesservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ordenesservice.ordenesservice.dto.ProductResponse;

@FeignClient(name = "productos-service")
public interface ProductClient {

    @GetMapping("/productos/{id}")
    ProductResponse getProductById(@PathVariable("id") String id);
}
