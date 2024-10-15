package com.luongchivi.blog_api.service;

import java.util.List;

import com.luongchivi.blog_api.dto.request.permission.PermissionCreationRequest;
import com.luongchivi.blog_api.dto.response.permission.PermissionResponse;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreationRequest request);

    List<PermissionResponse> getPermissions();

    void deletePermission(String permissionName);
}
