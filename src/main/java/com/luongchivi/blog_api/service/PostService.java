package com.luongchivi.blog_api.service;

import com.luongchivi.blog_api.dto.request.post.PostCreationRequest;
import com.luongchivi.blog_api.dto.response.post.PostResponse;

public interface PostService {
    PostResponse createPost(PostCreationRequest request);
}
