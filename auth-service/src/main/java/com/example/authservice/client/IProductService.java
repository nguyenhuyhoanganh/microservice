package com.example.authservice.client;

import com.example.authservice.dto.ProductDTO;
import com.example.authservice.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface IProductService {
//    @GetMapping("")
//    ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll();
}
