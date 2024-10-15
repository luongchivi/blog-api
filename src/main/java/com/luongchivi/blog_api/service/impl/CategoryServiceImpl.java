package com.luongchivi.blog_api.service.impl;

import com.luongchivi.blog_api.dto.request.category.CategoryCreationRequest;
import com.luongchivi.blog_api.dto.response.category.CategoryResponse;
import com.luongchivi.blog_api.entity.Category;
import com.luongchivi.blog_api.mapper.CategoryMapper;
import com.luongchivi.blog_api.repository.CategoryRepository;
import com.luongchivi.blog_api.service.CategoryService;
import com.luongchivi.blog_api.share.response.PageResponse;
import com.luongchivi.blog_api.share.utils.Utils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('Admin')")
    public CategoryResponse createCategory(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        category.setSlug(Utils.generateSlug(request.getName()));
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    public PageResponse<CategoryResponse> getCategories(int page, int pageSize, String sortDirection, String sortBy) {
        if (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC")) {
            throw new IllegalArgumentException("Invalid sort direction. Must be 'ASC' or 'DESC'.");
        }

        Sort sort;
        if (sortDirection.equalsIgnoreCase("DESC")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        List<CategoryResponse> categoriesResponse = categoriesPage.getContent().stream()
                .map(category -> categoryMapper.toCategoryResponse(category))
                .toList();
        return PageResponse.<CategoryResponse>builder()
                .currentPage(categoriesPage.getNumber() + 1)
                .totalPages(categoriesPage.getTotalPages())
                .pageSize(categoriesPage.getSize())
                .totalElements(categoriesPage.getTotalElements())
                .data(categoriesResponse)
                .build();
    }

    @PreAuthorize("hasRole('Admin')")
    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
