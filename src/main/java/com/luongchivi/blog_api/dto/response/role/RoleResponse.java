package com.luongchivi.blog_api.dto.response.role;

import java.time.LocalDateTime;
import java.util.Set;

import com.luongchivi.blog_api.dto.response.permission.PermissionResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
