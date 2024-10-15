package com.luongchivi.blog_api.dto.request.post;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreationRequest {
    String title;
    String content;
    Set<String> tags;
    Set<String> categories;
}
