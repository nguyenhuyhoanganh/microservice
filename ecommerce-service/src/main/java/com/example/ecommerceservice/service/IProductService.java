package com.example.ecommerceservice.service;

import com.example.ecommerceservice.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAll();
}
