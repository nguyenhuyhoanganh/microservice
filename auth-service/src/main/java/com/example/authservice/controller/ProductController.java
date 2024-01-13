package com.example.authservice.controller;

import com.example.authservice.client.IProductServiceClient;
import com.example.authservice.dto.ProductDTO;
import com.example.authservice.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductServiceClient serviceClient;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll(){
        return serviceClient.getAll();
    }
}
