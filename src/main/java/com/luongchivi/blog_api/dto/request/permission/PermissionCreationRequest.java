package com.luongchivi.blog_api.dto.request.permission;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionCreationRequest {
    String name;
    String description;
}
