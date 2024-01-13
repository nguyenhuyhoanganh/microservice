package com.example.authservice.client.impl;

import com.example.authservice.client.IProductServiceClient;
import com.example.authservice.dto.ProductDTO;
import com.example.authservice.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductServiceClientImpl implements IProductServiceClient {
    @Override
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll() {
        //fallback logic
        log.error("Ecommerce service has errors!");
        throw new RuntimeException("Ecommerce service has errors!");
    }
}
