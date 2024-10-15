package com.luongchivi.blog_api.service;

import com.luongchivi.blog_api.dto.request.category.CategoryCreationRequest;
import com.luongchivi.blog_api.dto.response.category.CategoryResponse;
import com.luongchivi.blog_api.share.response.PageResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreationRequest request);

    PageResponse<CategoryResponse> getCategories(int page, int pageSize, String sortDirection, String sortBy);

    void deleteCategory(String categoryId);
}
