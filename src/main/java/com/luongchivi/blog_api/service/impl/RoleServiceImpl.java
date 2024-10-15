package com.luongchivi.blog_api.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.luongchivi.blog_api.dto.request.role.RoleCreationRequest;
import com.luongchivi.blog_api.dto.response.role.RoleResponse;
import com.luongchivi.blog_api.entity.Permission;
import com.luongchivi.blog_api.entity.Role;
import com.luongchivi.blog_api.exception.AppException;
import com.luongchivi.blog_api.exception.ErrorCode;
import com.luongchivi.blog_api.mapper.RoleMapper;
import com.luongchivi.blog_api.repository.PermissionRepository;
import com.luongchivi.blog_api.repository.RoleRepository;
import com.luongchivi.blog_api.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public RoleResponse createRole(RoleCreationRequest request) {
        boolean existsByRole = roleRepository.existsById(request.getName());
        if (existsByRole) {
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTED);
        }

        Role role = roleMapper.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        Role saveRole = roleRepository.save(role);
        return roleMapper.toRoleResponse(saveRole);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public List<RoleResponse> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> roleMapper.toRoleResponse(role)).toList();
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
