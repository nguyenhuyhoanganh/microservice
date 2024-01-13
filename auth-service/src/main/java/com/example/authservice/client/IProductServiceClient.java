package com.example.authservice.client;

import com.example.authservice.client.impl.ProductServiceClientImpl;
import com.example.authservice.dto.ProductDTO;
import com.example.authservice.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ecommerce-service", fallback = ProductServiceClientImpl.class)
public interface IProductServiceClient {
    @GetMapping("/product")
    ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll();
}
