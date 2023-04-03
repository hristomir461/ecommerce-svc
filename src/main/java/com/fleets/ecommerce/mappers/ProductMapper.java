package com.fleets.ecommerce.mappers;

import com.fleets.ecommerce.entities.Product;
import com.fleets.ecommerce.models.Products.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(target = "category.id", source = "categoryId")
    Product toProduct(ProductDto dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "imageId", source = "image.id")
    ProductDto toDto(Product product);
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "imageId", source = "image.id")
    List<ProductDto> toDtos(List<Product> products);
}
