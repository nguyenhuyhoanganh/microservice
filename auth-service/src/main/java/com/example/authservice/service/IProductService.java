package com.example.authservice.service;

import com.example.authservice.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAll();
}
