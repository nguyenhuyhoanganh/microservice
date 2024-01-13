package com.example.authservice.client;

import com.example.authservice.dto.ProductDTO;
import com.example.authservice.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ecommerce-service", url = "http://localhost:8001/product")
public interface IProductServiceClient {
    @GetMapping("")
    ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll();
}
