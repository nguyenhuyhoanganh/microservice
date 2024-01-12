package com.example.base.service.impl;

import com.example.base.dto.ProductDTO;
import com.example.base.dto.UserDTO;
import com.example.base.entity.Product;
import com.example.base.entity.User;
import com.example.base.repository.ProductRepository;
import com.example.base.service.IProductService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public List<ProductDTO> getAll() {
        return repository.findAll().stream().map(product -> mapToDTO(product)).collect(Collectors.toList());
    }

    private ProductDTO mapToDTO(Product product) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(Conditions.isNotNull());
        mapper.createTypeMap(Product.class, ProductDTO.class).setPostConverter(context -> {
            User createdBy = context.getSource().getCreatedBy();
            User modifiedBy = context.getSource().getModifiedBy();
            context.getDestination().setCreatedBy(UserDTO.builder().id(createdBy.getId()).build());
            context.getDestination().setModifiedBy(UserDTO.builder().id(modifiedBy.getId()).build());
            return context.getDestination();
        });
        return mapper.map(product, ProductDTO.class);
    }
}
