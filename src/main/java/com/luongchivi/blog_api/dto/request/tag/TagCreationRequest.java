package com.luongchivi.blog_api.dto.request.tag;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagCreationRequest {
    @NotBlank(message = "TAG_NAME_REQUIRED")
    String name;
}
