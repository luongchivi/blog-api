package com.luongchivi.blog_api.service.impl;

import com.luongchivi.blog_api.dto.request.post.PostCreationRequest;
import com.luongchivi.blog_api.dto.response.post.PostResponse;
import com.luongchivi.blog_api.entity.Category;
import com.luongchivi.blog_api.entity.Post;
import com.luongchivi.blog_api.entity.Tag;
import com.luongchivi.blog_api.entity.User;
import com.luongchivi.blog_api.enums.PostStatus;
import com.luongchivi.blog_api.exception.AppException;
import com.luongchivi.blog_api.exception.ErrorCode;
import com.luongchivi.blog_api.mapper.PostMapper;
import com.luongchivi.blog_api.repository.CategoryRepository;
import com.luongchivi.blog_api.repository.PostRepository;
import com.luongchivi.blog_api.repository.TagRepository;
import com.luongchivi.blog_api.repository.UserRepository;
import com.luongchivi.blog_api.service.PostService;
import com.luongchivi.blog_api.share.utils.Utils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    PostRepository postRepository;

    UserRepository userRepository;

    TagRepository tagRepository;

    CategoryRepository categoryRepository;

    PostMapper postMapper;

    public PostResponse createPost(PostCreationRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Tìm nếu không có in ra lỗi
        Set<Tag> tags = request.getTags().stream()
                .map(tagName -> tagRepository.findOneByName(tagName)
                        .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND)))
                .collect(Collectors.toSet());

        Set<Category> categories = request.getCategories().stream()
                .map(categoryName -> categoryRepository.findOneByName(categoryName)
                        .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND))
                ).collect(Collectors.toSet());

        Post post = postMapper.toPost(request);

        post.setPostStatus(PostStatus.PENDING);
        post.setAuthor(user);
        post.setTags(tags);
        post.setCategories(categories);
        post.setSlug(Utils.generateSlug(request.getTitle()));

        Post savePost = postRepository.save(post);
        return postMapper.toPostResponse(savePost);
    }
}
