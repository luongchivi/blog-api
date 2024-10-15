package com.luongchivi.blog_api.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreationRequest {
    @NotBlank(message = "CATEGORY_NAME_REQUIRED")
    String name;
}
