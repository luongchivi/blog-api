package com.luongchivi.blog_api.dto.response.tag;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagResponse {
    String id;
    String name;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
