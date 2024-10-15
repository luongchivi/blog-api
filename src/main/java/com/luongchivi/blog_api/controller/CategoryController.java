package com.luongchivi.blog_api.controller;

import com.luongchivi.blog_api.dto.request.category.CategoryCreationRequest;
import com.luongchivi.blog_api.dto.response.category.CategoryResponse;
import com.luongchivi.blog_api.service.CategoryService;
import com.luongchivi.blog_api.share.response.ApiResponse;
import com.luongchivi.blog_api.share.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {
    CategoryService categoryService;

    @Operation(summary = "This endpoint create new categories")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ApiResponse<CategoryResponse> createTag(@RequestBody @Valid CategoryCreationRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return ApiResponse.<CategoryResponse>builder().results(categoryResponse).build();
    }

    @Operation(summary = "This endpoint get list categories")
    @GetMapping()
    public ApiResponse<PageResponse<CategoryResponse>> getTags(
            @RequestParam(value = "page", required = false, defaultValue = "1")
            @Min(value = 1, message = "PAGE_INVALID")
            int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10")
            @Min(value = 1, message = "PAGE_SIZE_INVALID")
            int pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        PageResponse<CategoryResponse> categories = categoryService.getCategories(page, pageSize, sort, sortBy);
        return ApiResponse.<PageResponse<CategoryResponse>>builder().results(categories).build();
    }

    @Operation(summary = "This endpoint delete category")
    @DeleteMapping("/{categoryId}")
    public ApiResponse deleteTag(@PathVariable("categoryId") String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.builder().message("Delete category successfully.").build();
    }
}
