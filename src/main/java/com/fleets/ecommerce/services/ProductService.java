package com.fleets.ecommerce.services;

import com.fleets.ecommerce.exceptions.ProductNotFoundException;
import com.fleets.ecommerce.entities.Product;
import com.fleets.ecommerce.mappers.ProductMapper;
import com.fleets.ecommerce.models.Products.ProductDto;
import com.fleets.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(ProductDto request) {
        Product product = ProductMapper.INSTANCE.toProduct(request);
        return productRepository.save(product);
    }

    public Product getById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("There is no product with id " + id));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
    public Boolean exist(Long id){
        return productRepository.existsById(id);
    }

    public Product update(ProductDto request) {
        Product product = ProductMapper.INSTANCE.toProduct(request);
        return productRepository.save(product);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }
}
