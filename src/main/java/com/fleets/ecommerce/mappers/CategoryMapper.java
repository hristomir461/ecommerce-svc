package com.fleets.ecommerce.mappers;

import com.fleets.ecommerce.entities.Category;
import com.fleets.ecommerce.models.Categories.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    Category toCategory(CategoryDto dto);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDtos(List<Category> categories);
}
