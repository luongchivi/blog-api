package com.luongchivi.blog_api.dto.response.post;

import com.luongchivi.blog_api.dto.response.category.CategoryResponse;
import com.luongchivi.blog_api.dto.response.tag.TagResponse;
import com.luongchivi.blog_api.dto.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String id;
    String title;
    String content;
    String postStatus;
    String slug;
    LocalDateTime publicationDate;
    UserResponse author;
    Set<CategoryResponse> categories;
    Set<TagResponse> tags;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
