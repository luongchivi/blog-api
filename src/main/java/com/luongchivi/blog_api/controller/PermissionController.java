package com.luongchivi.blog_api.controller;

import java.util.List;

import com.luongchivi.blog_api.dto.request.permission.PermissionCreationRequest;
import org.springframework.web.bind.annotation.*;

import com.luongchivi.blog_api.dto.response.permission.PermissionResponse;
import com.luongchivi.blog_api.service.PermissionService;
import com.luongchivi.blog_api.share.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Permission")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping()
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionCreationRequest request) {
        PermissionResponse permission = permissionService.createPermission(request);
        return ApiResponse.<PermissionResponse>builder().results(permission).build();
    }

    @GetMapping()
    public ApiResponse<List<PermissionResponse>> getUsers() {
        List<PermissionResponse> permissions = permissionService.getPermissions();
        return ApiResponse.<List<PermissionResponse>>builder()
                .results(permissions)
                .build();
    }

    @DeleteMapping("/{permissionName}")
    public ApiResponse updateUser(@PathVariable("permissionName") String permissionName) {
        permissionService.deletePermission(permissionName);
        return ApiResponse.builder().message("Delete permission successfully.").build();
    }
}
