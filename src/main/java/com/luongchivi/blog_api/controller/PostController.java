package com.luongchivi.blog_api.controller;

import com.luongchivi.blog_api.dto.request.post.PostCreationRequest;
import com.luongchivi.blog_api.dto.response.post.PostResponse;
import com.luongchivi.blog_api.service.PostService;
import com.luongchivi.blog_api.share.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Post")
public class PostController {
    PostService postService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody @Valid PostCreationRequest request) {
        PostResponse postResponse = postService.createPost(request);
        return ApiResponse.<PostResponse>builder().results(postResponse).build();
    }
}
