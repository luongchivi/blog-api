package com.luongchivi.blog_api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.luongchivi.blog_api.dto.request.tag.TagCreationRequest;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.service.TagService;
import com.luongchivi.blog_api.share.response.ApiResponse;
import com.luongchivi.blog_api.share.response.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/tags")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Tag")
public class TagController {
    TagService tagService;

    @Operation(summary = "This endpoint create new tags")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ApiResponse<TagResponse> createTag(@RequestBody @Valid TagCreationRequest request) {
        TagResponse tagResponse = tagService.createTag(request);
        return ApiResponse.<TagResponse>builder().results(tagResponse).build();
    }

    @Operation(summary = "This endpoint get list tags")
    @GetMapping()
    public ApiResponse<PageResponse<TagResponse>> getTags(
            @RequestParam(value = "page", required = false, defaultValue = "1")
                    @Min(value = 1, message = "PAGE_INVALID")
                    int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10")
                    @Min(value = 1, message = "PAGE_SIZE_INVALID")
                    int pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") String sort,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        PageResponse<TagResponse> tags = tagService.getTags(page, pageSize, sort, sortBy);
        return ApiResponse.<PageResponse<TagResponse>>builder().results(tags).build();
    }

    @Operation(summary = "This endpoint delete tag")
    @DeleteMapping("/{tagId}")
    public ApiResponse deleteTag(@PathVariable("tagId") String tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.builder().message("Delete tag successfully.").build();
    }
}
