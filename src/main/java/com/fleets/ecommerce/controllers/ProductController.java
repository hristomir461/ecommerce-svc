package com.fleets.ecommerce.controllers;

import com.fleets.ecommerce.exceptions.CategoryNotFoundException;
import com.fleets.ecommerce.exceptions.ProductNotFoundException;
import com.fleets.ecommerce.mappers.ProductMapper;
import com.fleets.ecommerce.models.Products.ProductDto;
import com.fleets.ecommerce.services.CategoryService;
import com.fleets.ecommerce.services.ImageService;
import com.fleets.ecommerce.services.ProductService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
            var response = ProductMapper.INSTANCE.toDto(productService.getById(id));
           return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto request) {
        if(categoryService.exist(request.getCategoryId())){
            var product = productService.create(request);
            var response = ProductMapper.INSTANCE.toDto(product);
            return ResponseEntity.ok().body(response);
        }
        throw new CategoryNotFoundException("There is no category with id "+ request.getCategoryId());
    }
    @PostMapping("/{id}/upload/image")
    public ResponseEntity createImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        imageService.uploadImageForProduct(id, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        var image = imageService.getById(id);
        return ResponseEntity.ok().body(image.getData());
    }
    @GetMapping("/all")
    public List<ProductDto> getAll() {
        return ProductMapper.INSTANCE.toDtos(productService.getAll());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto request) {
        if(categoryService.exist(request.getCategoryId())){
            if(productService.exist(id)){
                var product = productService.update(request);
                var response = ProductMapper.INSTANCE.toDto(product);
                return ResponseEntity.ok().body(response);
            }
            throw new ProductNotFoundException("There is no product with id "+ id);
        }
            throw new CategoryNotFoundException("There is no category with id "+ request.getCategoryId());
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
            if(productService.exist(id)){
                productService.deleteById(id);
                return ResponseEntity.ok().build();
            }
            throw new ProductNotFoundException("There is no product with id "+ id);
        }
}
