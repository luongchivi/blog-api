package com.luongchivi.blog_api.service.impl;

import java.util.List;

import com.luongchivi.blog_api.dto.request.permission.PermissionCreationRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.luongchivi.blog_api.dto.response.permission.PermissionResponse;
import com.luongchivi.blog_api.entity.Permission;
import com.luongchivi.blog_api.exception.AppException;
import com.luongchivi.blog_api.exception.ErrorCode;
import com.luongchivi.blog_api.mapper.PermissionMapper;
import com.luongchivi.blog_api.repository.PermissionRepository;
import com.luongchivi.blog_api.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public PermissionResponse createPermission(PermissionCreationRequest request) {
        boolean existsByPermission = permissionRepository.existsById(request.getName());
        if (existsByPermission) {
            throw new AppException(ErrorCode.PERMISSION_ALREADY_EXISTED);
        }

        Permission permission = permissionMapper.toPermission(request);
        Permission savePermission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(savePermission);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public List<PermissionResponse> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> permissionMapper.toPermissionResponse(permission))
                .toList();
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public void deletePermission(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
