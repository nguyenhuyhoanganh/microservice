package com.example.base.controller;

import com.example.base.dto.ProductDTO;
import com.example.base.dto.ResponseDTO;
import com.example.base.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService service;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll(){
        ResponseDTO<List<ProductDTO>> responseBody = ResponseDTO.<List<ProductDTO>>builder().data(service.getAll()).build();
        return new ResponseEntity<ResponseDTO<List<ProductDTO>>>(responseBody, HttpStatus.OK);
    }


}
