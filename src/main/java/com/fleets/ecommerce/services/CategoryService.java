package com.fleets.ecommerce.services;

import com.fleets.ecommerce.entities.Category;
import com.fleets.ecommerce.exceptions.CategoryNotFoundException;
import com.fleets.ecommerce.mappers.CategoryMapper;
import com.fleets.ecommerce.models.Categories.CategoryDto;
import com.fleets.ecommerce.repositories.CategoryRepository;
import com.fleets.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Category create(CategoryDto request) {
        Category category = CategoryMapper.INSTANCE.toCategory(request);
       return categoryRepository.save(category);
    }
    public Category update(CategoryDto request) {
        Category category = CategoryMapper.INSTANCE.toCategory(request);
        return categoryRepository.save(category);
    }
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("There is no category with id " + id));
    }
    public Boolean exist(Long id){
        return categoryRepository.existsById(id);
    }

    public void deleteById(Long id){
        var categoryProducts = this.getById(id).getProducts();
        if(!categoryProducts.isEmpty()){
            for (var product:categoryProducts) {
                productRepository.delete(product);
            }
        }
        categoryRepository.deleteById(id);
    }
}
