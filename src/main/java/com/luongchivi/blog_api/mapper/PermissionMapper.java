package com.luongchivi.blog_api.mapper;

import com.luongchivi.blog_api.dto.request.permission.PermissionCreationRequest;
import org.mapstruct.Mapper;

import com.luongchivi.blog_api.dto.response.permission.PermissionResponse;
import com.luongchivi.blog_api.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreationRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
