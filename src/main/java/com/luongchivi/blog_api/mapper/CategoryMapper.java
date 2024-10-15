package com.luongchivi.blog_api.mapper;

import com.luongchivi.blog_api.dto.request.category.CategoryCreationRequest;
import com.luongchivi.blog_api.dto.response.category.CategoryResponse;
import com.luongchivi.blog_api.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
