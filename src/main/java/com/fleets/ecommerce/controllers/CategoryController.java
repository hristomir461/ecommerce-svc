package com.fleets.ecommerce.controllers;

import com.fleets.ecommerce.exceptions.CategoryNotFoundException;
import com.fleets.ecommerce.exceptions.ProductNotFoundException;
import com.fleets.ecommerce.mappers.CategoryMapper;
import com.fleets.ecommerce.models.Categories.CategoryDto;
import com.fleets.ecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto request) {
            var category = categoryService.create(request);
            var response = CategoryMapper.INSTANCE.toDto(category);
            return ResponseEntity.ok().body(response);
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String mimeType = image.getContentType().split(".+?/(?=[^/]+$)")[1];
        if (mimeType.equals("png") || mimeType.equals("jpg") || mimeType.equals("jpeg")) {
            if(image.getSize() < 3000000) {
                String name = UUID.randomUUID() + "." +mimeType;
                Path fileNameAndPath = Paths.get("src/main/resources/static/images", name);
                Files.write(fileNameAndPath, image.getBytes());
                return "imageupload/index";
            }else {
                throw new IOException("Invalid size for image");
            }
        }else {
            throw new IOException("Invalid extension for image");
        }
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto request) {
        if(categoryService.exist(id)) {
            var category = categoryService.update(request);
            var response = CategoryMapper.INSTANCE.toDto(category);
            return ResponseEntity.ok().body(response);
        }
        throw new CategoryNotFoundException("There is no category with id " + id);
    }

    @GetMapping("/all")
    public List<CategoryDto> getAll() {
        return CategoryMapper.INSTANCE.toDtos(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
            return CategoryMapper.INSTANCE.toDto(categoryService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if(categoryService.exist(id)){
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        throw new ProductNotFoundException("There is no product with id "+ id);
    }
}